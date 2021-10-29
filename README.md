# NisumTest

## Functional Acceptance Criteria

* User can enter an acronym or initialism
* User is then presented with a list of corresponding meanings

## Api - http://www.nactem.ac.uk/software/acromine/rest.html

	* Project must be written in Native Android and Kotlin
	* Use MVVM architecture and Data binding
	* You can use any third-party component for networking module
	* Basic testcases should be added
	* Handle all the possible error cases

# Explanation of architecture

The architecture of the app is under MVI and MVVM, within this architecture we have the composition
of the project folders. Which are the main 4 with their subfolders.

* The "core" folder stores the objects that are used in any other part of the app and that have a
  direct link without the need to inject them.

* The "data" folder contains local components such as the local database built in Room Android, the
  API data models, and the repositories.

* The "di" folder has the injection modules that apply to other classes, it is built under dagger +
  hilt.

* The network folder has the API interfaces.

* The UI folder stores the states of the views, the viewModels and the fragments of the app, all the
  visual part.

![alt text](https://github.com/GeekGianca/android_abb_test/blob/main/n00.png?raw=true)

# How does the app work?

- App have Light mode or Night mode
- Recent search data is displayed on this screen.
- Tapping the search button displays the filter view
- When you tap the search, he will perform the search once we tap the search button, but he does not
  perform searches every time we write.
- If we tap on one of the results, it will show us the detail of the selected abbreviation
- When we return to the initial screen we will have local results
- If the application does not have an internet connection, but locally there are results stored with
  the search, it displays them, it also shows an error snackbar due to internet connection
  ![alt text](https://github.com/GeekGianca/android_abb_test/blob/main/n1_.png?raw=true)
  ![alt text](https://github.com/GeekGianca/android_abb_test/blob/main/n2_.png?raw=true)
  ![alt text](https://github.com/GeekGianca/android_abb_test/blob/main/n3_.png?raw=true)
  ![alt text](https://github.com/GeekGianca/android_abb_test/blob/main/n4_.png?raw=true)
  ![alt text](https://github.com/GeekGianca/android_abb_test/blob/main/n5_.png?raw=true)
  ![alt text](https://github.com/GeekGianca/android_abb_test/blob/main/n6_.png?raw=true)
  ![alt text](https://github.com/GeekGianca/android_abb_test/blob/main/n7_.png?raw=true)
  ![alt text](https://github.com/GeekGianca/android_abb_test/blob/main/n8_.png?raw=true)
  ![alt text](https://github.com/GeekGianca/android_abb_test/blob/main/n9_.png?raw=true)
