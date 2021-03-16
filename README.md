
# Workshop - CI/CD with Google Cloud Platform

This workshop repository contains exercises for a GCP DevOps CI/CD pipeline using mainly:


## Requirement

*   Google Cloud Platform User Account
*   Google Cloud Platform SDK (>= 331.0.0)
*	Docker
*	JAVA 1.8
*   Maven 3
*   Google Cloud project with enabled billing
*   Google Cloud SDK
*   Latest version of Git
*   Enable Cloud Source API

## Exercise 1 - Setting up a Cloud Source repository

Launch your Google Cloud SDK Shell and make sure you are using correct project by launching:

```
gcloud config set project PROJECT_ID
```

After this, in order to create a Google Cloud repository named *hello-world*, launch the following command in the Shell:

```
gcloud source repos create hello-world
```

Now, you can clone this remote repository, using:

```
gcloud source repos clone REPOSITORY_NAME --project=PROJECT_ID
```

The output should tell you where cloned repository is located. You should receive something like this:

```
Cloning into 'C:\Users\Workshop\AppData\Local\Google\Cloud SDK\hello-world'...
warning: You appear to have cloned an empty repository.
Project [workshop-307013] repository [hello-world] was cloned to [C:\Users\Workshop\AppData\Local\Google\Cloud SDK\hello-world].
```

At this point, we can add code in this local repository. Go to the location path returned from previous step and copy-paste, in this directory, the content of this GitHub repository. After that, launch the following commands in order to push all on remote Google Cloud Source Repository:

```
git add .
git commit -m "First commit"
git push origin master
```

As you can see, there is no more need to use Cloud Shell: it's simply Git.

Now, if you go in your GCP Console and access to Cloud Source Repository, you should see the newly created repository. Click on its name and let's see what it looks like:

![](C:\Users\PosillipoAn\Desktop\NTT-DevOps\Task-63-Workshop-Google-Source-Build-Registry\workshopUs63\assets\images\Capture1.PNG)

You can view many details, like: branches, all files in your repository, their history, every commit detail and more. In this exercise, we are going to use a special feature of Cloud Source Repository: **Search Code**.

You can search for specific files or code snippets by using the search box located at the top of the Google Cloud Console. When you begin to type into it, it requests you to choose your Scope.

The **Scope** lets you restrict your search scope to one of the following, depending on your location in the source repository interface:

- **Everything**: Searches all repositories where you have access.
- **This Project**: Searches all repositories in the current project.
- **The Repository**: Searches the current repository.
- **This Directory**: Searches the current directory.

You can search for a file in several ways. For example, you can use the `file` filter to search for a file by using its path. For example:

```
file:Book
```

You can also search for a file name by typing its name and extension. For example:

```
Book .java
```

To restrict your search results to a specific language, use the `language` or `lang` filter. For example, the following search restricts the search results to the Java language:

```
language:java
```

You can restrict your search to the contents of a file by using the `content` filter. For example, the following query looks for the term `main` in the contents of all Java files. It does not search for instances where a path contains the term `main`

```
lang:java content:main
```

You can make your search case-sensitive by using the `case` filter. For example, the following search returns only results that match the term `Book`. It excludes results where the case doesn't match.

```
case:yes Book
```

To search for a specific class, use the `class` keyword. For example, the following search returns all classes with the term `Book`.

```
class:Book
```

Use the `function` or `func` filters to search for a specific function. For example, the following search returns all functions with the term `main`.

```
function:main
```

To exclude a term from search results, prepend the `-` character to the term you want to exclude. For example, the following search returns all functions with the term `main`, but excludes matches found in C++ files.

```
function:main -lang:cpp
```

Enclose your search terms in double quotation marks (`"`) to perform a literal search. For example, the following example searches for the term `book`.

```
"book"
```

You can search for multiple terms by using the `AND` operator. This operator returns results only when the terms on both sides of the operator are true. For example, the following search returns Java files that contain the term `main`.

```
main AND lang:java
```

A search for multiple terms uses `AND` implicitly. For example, you could write the preceding example as follows:

```
main lang:java
```

The `OR` operator returns a result if it matches an expression on either side of the keyword. For example, the following search returns files that contain the term `book` or the term `main`.

```
book OR main
```

You can group multiple search terms together using parentheses (`(` and `)`). For example:

```
(book OR main) AND lang:java
```

For more details, go to: https://cloud.google.com/source-repositories/docs/searching-code