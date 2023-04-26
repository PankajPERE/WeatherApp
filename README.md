**WeatherApp**

This Application will provide you Weather update. Initially you need to register user into this as a new user.
Once you login you will be able to see two tabs. The first tab will show current weather and second will be show 
list of weather history data which collected by user when user opens the application.

This aaplication also provide some other features such as:

1.Current location(City, Country)

2.Depending on the weather, Aplication shows the weather icon.

3.Current Weather status.

4.Current temprature in Celsius.

5.Time of Sunrise and Sunset.

6.Save weather detail everytime when user opens the aaplication.

**Project Details**

- Architecture: MVVM

- Android Studio Version: Android Studio Flamingo|2022.2.1

- Programming Language: Kotlin(213-1.7.20 and above)

- Device Supports: Android phone(Potrait Orientation)


**NOTE**

You need to use your own OpenWeather API Key at below mention path.

gradle.properties -> API_KEY 

Also update the Pass Code for Database encryption at below mention path.

gradle.properties -> DB_PASS


**Project Structure**

Inside main folder we have java->com->example->weatherapppankaj

- Uder container folder we have Base application file.

- Under database folder we have database logic, dao, and entities.

- Under di folder we have covered dependancy injection for Retrofit and Room DB

- Under model folder we have all weather models.

- Under network folder we have apiInterface.

- Under repository folder we have userRepository and weatherRepository.

- Under ui folder we have all activity, fragments, and adapters.

- Under utils folder we have covered all commonly used util classes.

- Under viewmodel folder we have viewmodels for login and weather.


![projectStructure](https://user-images.githubusercontent.com/131420205/234472846-523c4c49-9418-4c17-b5df-3410c591468a.png)


**Security**

User data is protected by SQLCipher

**Unit Test**

Unit test written for Room Database, viewmodels, util, dao, retrofit. Above 90% test coverage
![Screenshot (13)](https://user-images.githubusercontent.com/131420205/234472778-ebadd7d6-34b8-4ab9-876b-a2ba91838b3d.png)


**App Screens**

![login](https://user-images.githubusercontent.com/131420205/234472930-e8bb280f-d65b-4cd2-a15d-6d46d9d40992.png)

![signup](https://user-images.githubusercontent.com/131420205/234472953-9d380e5e-c8ef-4169-a328-e82b022f76eb.png)

![weather_home](https://user-images.githubusercontent.com/131420205/234472971-5a83f1eb-53a5-4cb0-af59-e4995aa69b04.png)

![weather_history](https://user-images.githubusercontent.com/131420205/234472989-b02c98cb-e285-44ea-8d76-caa0b93d6368.png)


**NOTE:** Being only contributor I have put all code in master directly.
