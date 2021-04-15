
# Workshop - CI/CD with Google Cloud Platform

This workshop repository contains exercises for a GCP DevOps CI/CD pipeline using mainly:


## Requirement

*   Google Cloud Platform User Account
*   Google Cloud Platform SDK (>= 331.0.0)
*	Docker
*	JAVA 1.8
*   Maven 3

## Excercise 6 - Deploying from Artifact Registry to GKE

##### 1. Creating a GCP Docker Artifact repository
In order to start push Docker images a Docker repository must be created executing the below `gcloud` command:

	gcloud artifacts repositories create docker-repository \
	--repository-format=docker \
	--description="GCP Artifacts repository for Docker images" \
	--location europe-west4

After creating the Docker repository you can check for the created repository with `gcloud artifacts repositories list` and set it as defualt for your Cloud SDK

List repositoies:

	gcloud artifacts repositories list --location europe-west4

	REPOSITORY         FORMAT  DESCRIPTION                                 LOCATION      LABELS  ENCRYPTION          	CREATE_TIME          UPDATE_TIME
	docker-repository  DOCKER  GCP Artifacts repository for Docker images  europe-west4          Google-managed key  2021-03-13T15:57:43  2021-03-13T15:57:43

Set as default
	
	gcloud config set artifacts/repository docker-repository

After creating the Docker repository you can check for executing the `gcloud artifacts repositories list` command and set it as defualt for your Cloud SDK

List repositoies:

	gcloud artifacts repositories list --location europe-west4

	REPOSITORY         FORMAT  DESCRIPTION                                 LOCATION      LABELS  ENCRYPTION          	CREATE_TIME          UPDATE_TIME
	docker-repository  DOCKER  GCP Artifacts repository for Docker images  europe-west4          Google-managed key  2021-03-13T15:57:43  2021-03-13T15:57:43

Set as default
	
	gcloud config set artifacts/repository docker-repository
	

###### Pushing Docker image to a GCP Artifact Docker repository
To be able to push builded docker images Docker must be configured running `gcloud auth configure-docker` command as below

	gcloud auth configure-docker europe-west4-docker.pkg.dev
	
Response:

	Adding credentials for: europe-west4-docker.pkg.dev
	After update, the following will be written to your Docker config file
	 located at [/root/.docker/config.json]:
	 {
	  "credHelpers": {
	    "staging-k8s.gcr.io": "gcloud",
	    "us.gcr.io": "gcloud",
	    "marketplace.gcr.io": "gcloud",
	    "europe-west4-docker.pkg.dev": "gcloud",
	    "gcr.io": "gcloud",
	    "asia.gcr.io": "gcloud",
	    "eu.gcr.io": "gcloud"
	  }
	}
	
	Do you want to continue (Y/n)?  y
	
	Docker configuration file updated.	
	
After configuring **Docker** your are able to build and push the image to your **Google Artifacts** repository pointing to the repository as:

	LOCATION-.pkg.dev/PROJECT/REPOSITORY/IMAGE

Example:

	europe-west4-docker.pkg.dev/workshop-307013/docker-repository/workshop-us63:1.1.0
  	

###### Pushing Docker Image to **Artifact Registry**

	# Build Docker image
	docker build -t workshop-us63 .
	
	# Tag the image
	docker tag workshop-us63 europe-west4-docker.pkg.dev/workshop-307013/docker-repository/workshop-us63:1.6.0
	
	# Push to Docker repository
	docker push europe-west4-docker.pkg.dev/workshop-307013/docker-repository/workshop-us63:1.6.0
	

###### Deploying container image to Google Cloud Kubernetes (GKE)
After adding the pushed Docker image on Artifact Registry your are able to deploy to GCP using GKE or others available compute services

In this case we'll deploy to GKE executing the following steps:

###### 1. Configuring kubectl:
	gcloud container clusters get-credentials <GKE_CLUSTER_ID> --zone <CLUSTER_ZONE>

###### 2. Set the target GKE cluster:
After credentials configuration, choose the target cluster to use from the listed contexts as `kubectl config get-contexts` as shown below:

	CURRENT   NAME                                                        CLUSTER                                     AUTHINFO                                     
	*         gke_workshop-307013_europe-west4-a_workshop-gke-cluster-1   gke_workshop-307013_europe-west4-...   

After listing the available contexts switch to the target context choosing between the available ones under the **NAME** column as below:

	kubectl config use-context gke_workshop-307013_europe-west4-a_workshop-gke-cluster-1

###### 3. Creating a Kubernetes deployment:
After configuring kubectl to point the target cluster, run the `kubectl apply`
	kubectl apply -f gke/deployment.yaml

###### 4. Checking Kubernetes resources

	kubectl get pod

	NAME                                READY   STATUS    RESTARTS   AGE
	workshop-service-5b7698fc94-dmssj   1/1     Running   0          5m15s
	workshop-service-5b7698fc94-pvxdg   1/1     Running   0          5m20s

	kubectl get service

	NAME               TYPE        CLUSTER-IP    EXTERNAL-IP   PORT(S)    AGE	
	workshop-service   ClusterIP   10.96.12.37   <none>        8080/TCP   19m

###### 5. Configuring network access to the created resources
To access to a ClusterIP service from your local machine execute `kubectl port-forward` in order to forward TCP 8080 traffic to the GKE cluster as shown below:

	kubectl port-forward service/workshop-service 8080:8080

###### Testing the service
After deploying and enabled port-forwarding you are able to test the deployed service choosing between the following options:

1. Accessing to the workshop service index page available at
	`http://127.0.0.1:8080/workshop-us63/frontend/index.html`
2. Executing `curl` request as`curl --location --request GET 'http://127.0.0.1:8080/workshop-us63/books'`

##### 7. Clean up
To avoid resource consumptions and costs delete the created GKE resources after testing:

- **Delete gke service:**

	kubectl delete all -l app=workshop-service

- **Delete docker images:**

	  gcloud artifacts docker images delete europe-west4-docker.pkg.dev/workshop-307013/docker-repository/workshop-us63:1.6.0

- **Delete the created Docker repository**:

	  gcloud artifacts repositories delete docker-repository --location europe-west4