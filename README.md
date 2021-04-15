
# Workshop - CI/CD with Google Cloud Platform

This workshop repository contains exercises for a GCP DevOps CI/CD pipeline using mainly:


## Requirement

*	Google Cloud Platform User Account
*	Google Cloud Platform SDK (>= 331.0.0)
*	Google Beta Commands
*	Docker
*	JAVA 1.8
*	Maven 3

## Exercise 9 - Working with Cloud Build: build and deploy a Docker image
The purpose of this exercise is to build and deploy the Docker image of your application to **GKE**

#### 1. Enabling Cloud Build Service

Before starting, you need to enable the cloud folllowing cloud services:

	gcloud services enable cloudbuild.googleapis.com
	gcloud services enable artifactregistry.googleapis.com
	  
#### 2. Defining the build config
	  
Then, to build using Cloud Build, you need to define a build config file (`cloudbuild.yaml` or `cloudbuild.json`) in your project sources with the following structure:

	steps:
	- name: 'gcr.io/cloud-builders/docker'
	  args: ['build', '-t', '${_LOCATION}-docker.pkg.dev/$PROJECT_ID/${_REPOSITORY}/${_IMAGE}:${_TAG}', '.']
	- name: 'gcr.io/cloud-builders/docker'
	  args: ['push', '${_LOCATION}-docker.pkg.dev/$PROJECT_ID/${_REPOSITORY}/${_IMAGE}:${_TAG}']
	- name: "gcr.io/cloud-builders/gke-deploy"
	args:
	- run
	- --filename=gke/deployment.yaml
	- --location=${_LOCATION}
	- --cluster=${_GKE_CLUSTER}
	substitutions:
		_LOCATION: europe-west4-a
		_GKE_CLUSTER: workshop-gke-cluster-1
	  
**NOTE** that, given we are using a _**custom**_ runtime, you cannot have a `cloudbuild.yaml` and a `Dockerfile` in the same source folder; for this reason you can create a _**cloudbuild**_ folder where to put the `cloudbuild.yaml` you just created and point to this file when creating the build trigger.

#### 3. Creating the build trigger

To be able to run the build as you defined, you can create a build trigger that will execute any time it detects a push on the branch on which you configure it. You can create it running `gcloud builds triggers create` command as follows:

	gcloud beta builds triggers create cloud-source-repositories --name deploy-image /
	--repo github_inganto93_workshopus63 /
	--branch-pattern exercise-9 /
	--build-config cloudbuild/cloudbuild.yaml /
	--substitutions _LOCATION=europe-west4,_REPOSITORY=docker-repository,_IMAGE=workshop-us63,_TAG=1.9.0
	
Response:

	Created [https://cloudbuild.googleapis.com/v1/projects/workshop-307013/triggers/12270419-b97d-4878-96a2-b8218b764833].
	NAME          CREATE_TIME                STATUS
	deploy-image  2021-03-16T11:20:49+00:00
	
After creating the trigger you can check it using the command:

	gcloud beta builds triggers describe deploy-image
	
You should get a response similar to the following:

	createTime: '2021-03-16T11:20:49.779035337Z'
	filename: cloudbuild/cloudbuild.yaml
	id: 12270419-b97d-4878-96a2-b8218b764833
	name: deploy-image
	substitutions:
	  _IMAGE: workshop-us63
	  _LOCATION: europe-west4
	  _REPOSITORY: docker-repository
	  _TAG: 1.9.0
	triggerTemplate:
	  branchName: exercise-9
	  projectId: workshop-307013
	  repoName: github_inganto93_workshopus63

You can also run this trigger manually using this command:

	gcloud beta builds triggers run deploy-image
	
#### 4. Triggering the build

Now that everything is set up, push any modification on your branch to trigger the build.

For instance, try to change the color of the application background in the file `workshopUs63\src\main\resources\public\frontend\bootstrap\css\bootstrap.min.css`.

You can access the GCP console and go to the Cloud Build section to check that a build started from your trigger; choose it from the builds list to see its details:

![N|Solid](assets/images/build.png)

#### 5. Checking deployed application
After deployed you can check the deployed services through the [Google Cloud Console](https://console.cloud.google.com/) or enabling the Kubernetes port forward following the steps below:

###### 1. Configuring kubectl:
	gcloud container clusters get-credentials <GKE_CLUSTER_ID> --zone <CLUSTER_ZONE>

###### 2. Set the target GKE cluster:
After credentials configuration, choose the target cluster to use from the listed contexts as `kubectl config get-contexts` as shown below:

	CURRENT   NAME                                                        CLUSTER                                     AUTHINFO                                     
	*         gke_workshop-307013_europe-west4-a_workshop-gke-cluster-1   gke_workshop-307013_europe-west4-...   

After listing the available contexts switch to the target context choosing between the available ones under the **NAME** column as below:

	kubectl config use-context gke_workshop-307013_europe-west4-a_workshop-gke-cluster-1

###### 3. Checking created GKE resources

	kubectl get pod

	NAME                                READY   STATUS    RESTARTS   AGE
	workshop-service-5b7698fc94-dmssj   1/1     Running   0          5m15s
	workshop-service-5b7698fc94-pvxdg   1/1     Running   0          5m20s

	kubectl get service

	NAME               TYPE        CLUSTER-IP    EXTERNAL-IP   PORT(S)    AGE	
	workshop-service   ClusterIP   10.96.12.37   <none>        8080/TCP   19m

###### 4. Configuring network access to the deployed service
To access to a ClusterIP service from your local machine execute `kubectl port-forward` in order to forward TCP 8080 traffic to the GKE cluster as shown below:

	kubectl port-forward service/workshop-service 8080:8080

###### 6. Testing the service
After deploying and enabled port-forwarding you are able to test the deployed service choosing between the following options:

1. Accessing to the workshop service index page available at: `http://127.0.0.1:8080/workshop-us63/frontend/index.html`
2. Executing `curl` request: `curl --location --request GET 'http://127.0.0.1:8080/workshop-us63/books'`


#### 7. Cleaning up resources

After checking the application, delete it to avoid resource consumptions and costs.

##### Delete gke resources:

	kubectl delete all -l app=workshop-service

##### Delete trigger:

	gcloud beta builds triggers delete deploy-image

##### Delete docker image:

	gcloud artifacts docker images delete europe-west4-docker.pkg.dev/workshop-307013/docker-repository/workshop-us63:1.9.0

If previously tagged images have not been deleted, you need to force tag deletion using the option _--delete-tags_:

	gcloud artifacts docker images delete europe-west4-docker.pkg.dev/workshop-307013/docker-repository/workshop-us63:1.9.0 --delete-tags	
