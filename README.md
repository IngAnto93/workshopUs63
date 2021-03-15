
# Workshop - CI/CD with Google Cloud Platform

This workshop repository contains exercises for a GCP DevOps CI/CD pipeline using mainly:


## Requirement

*   Google Cloud Platform User Account
*   Google Cloud Platform SDK (>= 331.0.0)
*	Docker
*	JAVA 1.8
*   Maven 3

## Excercise 1 - Working with artifact registry
To start to push your Docker or specific language artifacts you need to create a repository using the `gcloud artifacts` command 

1.  **Creating a GCP Docker Artifact repository** 

	gcloud artifacts repositories create docker-repository \
	--repository-format=docker \
	--description="GCP Artifacts repository for Docker images" \
	--location europe-west4

2.  **Creating a GCP Maven Artifact repository** 

	gcloud artifacts repositories create maven-repository \
	--repository-format=maven \
	--description="GCP Artifacts repository for Maven artifacts" \
	--location europe-west4

After creating the Docker repository you can check for the created repository with `gcloud artifacts repositories list` and set it as defualt for your Cloud SDK

List repositoies:

	gcloud artifacts repositories list --location europe-west4

	REPOSITORY         FORMAT  DESCRIPTION                                 LOCATION      LABELS  ENCRYPTION          	CREATE_TIME          UPDATE_TIME
	docker-repository  DOCKER  GCP Artifacts repository for Docker images  europe-west4          Google-managed key  2021-03-13T15:57:43  2021-03-13T15:57:43

Set as default
	
	gcloud config set artifacts/repository docker-repository
	

#####	Pushing Docker image to a GCP Artifact Docker repository
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
  	

###### Pushing Docker Image to **Google Artifacts**

	# Build Docker image
	docker build -t workshop-us63 .
	
	# Tag the image
	docker tag workshop-us63 europe-west4-docker.pkg.dev/workshop-307013/docker-repository/workshop-us63:1.1.0
	
	# Push to Docker repository
	docker push europe-west4-docker.pkg.dev/workshop-307013/docker-repository/workshop-us63:1.1.0
	

#### Deploy to APP Engine
After pushed to Artifact Registry you are able to deploy using app engine





After deployed you can test the service using a `cURL` HTTP request

	curl --location --request GET 'https://workshop-us63-dot-workshop-307013.ey.r.appspot.com/workshop-us63/book