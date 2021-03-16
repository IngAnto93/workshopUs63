
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

To achieve this you need to define an `app.xml` describing the features of the application you are going to deploy:

	runtime: custom
	env: flexible 
	service: workshop-us63
	resources:
	  memory_gb: 1.5
	  
Then, you need to create a build config file (`cloudbuild.yaml` or `cloudbuild.json`) in your project sources with the following structure:

	steps:
	- name: 'gcr.io/cloud-builders/gcloud'
	  args: ['app', 'deploy', '--project', '$PROJECT_ID']
	images:
	  - '${_REGION}-docker.pkg.dev/$PROJECT_ID/${_REPOSITORY}/${_IMAGE}:${_TAG}'
	  
**NOTE** that, given we are using a _**custom**_ runtime, you cannot have a `cloudbuild.yaml` and a `Dockerfile` in the same source folder; for this reason you can create a _**cloudbuild**_ folder where to put the `cloudbuild.yaml` you just created and point to this file when creating the build trigger.

#### Creating the build trigger
To be able to run the build as we defined, you can create a build trigger that will execute any time it detects a push on the branch with you configure it. You can create it running `gcloud builds triggers create` command as below:

	gcloud beta builds triggers create cloud-source-repositories --name deploy-image /
	--repo github_inganto93_workshopus63 /
	--branch-pattern exercise-9 /
	--build-config cloudbuild/cloudbuild.yaml /
	--substitutions _REGION=europe-west4,_REPOSITORY=docker-repository,_IMAGE=workshop-us63,_TAG=1.1.0
	
Response:

	Created [https://cloudbuild.googleapis.com/v1/projects/workshop-307013/triggers/12270419-b97d-4878-96a2-b8218b764833].
	NAME          CREATE_TIME                STATUS
	deploy-image  2021-03-16T11:20:49+00:00
	
After creating the trigger you can check it using the command `gcloud beta builds triggers describe deploy-image`; you should get a response similar to the following:

	createTime: '2021-03-16T11:20:49.779035337Z'
	filename: cloudbuild/cloudbuild.yaml
	id: 12270419-b97d-4878-96a2-b8218b764833
	name: deploy-image
	substitutions:
	  _IMAGE: workshop-us63
	  _REGION: europe-west4
	  _REPOSITORY: docker-repository
	  _TAG: 1.1.0
	triggerTemplate:
	  branchName: exercise-9
	  projectId: workshop-307013
	  repoName: github_inganto93_workshopus63

You can also run this trigger manually use this command `gcloud beta builds triggers run deploy-image`.

#### Check deployed application
You can test that the deployed application is running at:

	https://workshop-us63-dot-workshop-307013.ey.r.appspot.com/workshop-us63
