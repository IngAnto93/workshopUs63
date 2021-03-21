
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
The purpose of this exercise is to build and deploy the Docker image of your application to **App Engine**.

#### 1. Enabling Cloud Build Service

Before starting, you need to enable the cloud services (if not already enabled) related to Cloud Build, Artifact Registry and App Engine executing the following command(s):

	gcloud services enable cloudbuild.googleapis.com
	gcloud services enable artifactregistry.googleapis.com
	gcloud services enable appengine.googleapis.com	
	
#### 2. Defining the app config

To run an application on App Engine you need to define `app.xml` describing the features of the application you are going to deploy:

	runtime: custom
	env: flexible 
	service: workshop-us63-exercise-9
	resources:
	  memory_gb: 1.5
	  
#### 3. Defining the build config
	  
Then, to build using Cloud Build, you need to define a build config file (`cloudbuild.yaml` or `cloudbuild.json`) in your project sources with the following structure:

	steps:
	- name: 'gcr.io/cloud-builders/docker'
	  args: ['build', '-t', '${_LOCATION}-docker.pkg.dev/$PROJECT_ID/${_REPOSITORY}/${_IMAGE}:${_TAG}', '.']
	- name: 'gcr.io/cloud-builders/docker'
	  args: ['push', '${_LOCATION}-docker.pkg.dev/$PROJECT_ID/${_REPOSITORY}/${_IMAGE}:${_TAG}']
	- name: 'gcr.io/cloud-builders/gcloud'
	  args: ['app', 'deploy', '--project', '$PROJECT_ID', '--image-url', '${_LOCATION}-docker.pkg.dev/$PROJECT_ID/${_REPOSITORY}/${_IMAGE}:${_TAG}']
	  
**NOTE** that, given we are using a _**custom**_ runtime, you cannot have a `cloudbuild.yaml` and a `Dockerfile` in the same source folder; for this reason you can create a _**cloudbuild**_ folder where to put the `cloudbuild.yaml` you just created and point to this file when creating the build trigger.

#### 4. Creating the build trigger

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
	
#### 5. Triggering the build

Now that everything is set up, push any modification on your branch to trigger the build.

For instance, try to change the color of the application background in the file `workshopUs63\src\main\resources\public\frontend\bootstrap\css\bootstrap.min.css`.

You can access the GCP console and go to the Cloud Build section to check that a build started from your trigger; choose it from the builds list to see its details:

![N|Solid](assets/images/build.png)

#### 6. Checking deployed application

Once the build has successfully completed, you can check that the deployed application is running at with the background color you just changed:

	https://workshop-us63-dot-workshop-307013.ey.r.appspot.com/workshop-us63-exercise-9

#### 7. Cleaning up resources

After checking the image, delete it to avoid resource consumptions and costs.

##### Delete trigger:

	gcloud beta builds triggers delete build-push-image

##### Delete docker image:

	gcloud artifacts docker images delete europe-west4-docker.pkg.dev/workshop-307013/docker-repository/workshop-us63:1.9.0

If previously tagged images have not been deleted, you need to force tag deletion using the option _--delete-tags_:

	gcloud artifacts docker images delete europe-west4-docker.pkg.dev/workshop-307013/docker-repository/workshop-us63:1.9.0 --delete-tags	
	
##### Delete app engine service:

	gcloud app services delete workshop-us63-exercise-9
