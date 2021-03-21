
# Workshop - CI/CD with Google Cloud Platform

This workshop repository contains exercises for a GCP DevOps CI/CD pipeline using mainly:


## Requirement

*	Google Cloud Platform User Account
*	Google Cloud Platform SDK (>= 331.0.0)


## Exercise 10 - Build insight
The purpose of this exercise is to use the Google Cloud Console to view information about your builds and their logs.

#### Listing builds

You can view all builds using the following command:

	gcloud builds list

You should get an output as the following:

![alt text for screen readers](img/buildlist.JPG "gcloud builds list")

You can also filter this list using the option __--filter__, for example:

*	`gcloud builds list --filter status="FAILURE"`

	to view only builds whose compilation failed 

*	`gcloud builds list --filter "create_time>"2021-03-15T00:00:00+00:00" AND create_time<"2021-03-16T18:43:49+00:00""`

	to view all builds created between 2021/03/15 and 2021/03/16

#### Viewing build details

If you want to see the details of a specific build, you can use the command:

	gcloud builds describe [BUILD_ID]

You can retrieve the build id from the list you got with the previous operation.

	gcloud builds describe da019b15-a210-4f04-a420-8fad8d9574ed

As response, you get all the informations about your build:

![alt text for screen readers](img/describe.JPG "gcloud builds describe [BUILD_ID]")	
