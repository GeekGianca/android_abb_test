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
The architecture of the app is under MVI and MVVM, within this architecture we have the composition of the project folders.
Which are the main 4 with their subfolders.

* The "core" folder stores the objects that are used in any other part of the app and that have a direct link without the need to inject them.

* The "data" folder contains local components such as the local database built in Room Android, the API data models, and the repositories.

* The "di" folder has the injection modules that apply to other classes, it is built under dagger + hilt.

* The network folder has the API interfaces.

* The UI folder stores the states of the views, the viewModels and the fragments of the app, all the visual part.

# How does the app work?
- App have Light mode or Night mode
- Recent search data is displayed on this screen.
- Tapping the search button displays the filter view
  ![alt text](https://github.com/GeekGianca/offline_mode.png?raw=true)
  
- When you tap the search, he will perform the search once we tap the search button, but he does not perform searches every time we write.
  ![alt text](https://github.com/GeekGianca/offline_mode.png?raw=true)
  
- If we tap on one of the results, it will show us the detail of the selected abbreviation
  ![alt text](https://github.com/GeekGianca/offline_mode.png?raw=true)
  
- When we return to the initial screen we will have local results
  ![alt text](https://github.com/GeekGianca/offline_mode.png?raw=true)
  
- If the application does not have an internet connection, but locally there are results stored with the search, it displays them, it also shows an error snackbar due to internet connection