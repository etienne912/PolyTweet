<h1 align="center">PolyTweet 🖧</h1>

![GitHub Workflow Status](https://img.shields.io/github/workflow/status/etienne912/PolyTweet/CI)
![GitHub](https://img.shields.io/github/license/etienne912/PolyTweet)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/etienne912/PolyTweet)
[![GitHub issues](https://img.shields.io/github/issues/etienne912/PolyTweet)](https://github.com/etienne912/PolyTweet/issues)
[![GitHub forks](https://img.shields.io/github/forks/etienne912/PolyTweet)](https://github.com/etienne912/PolyTweet/network)
[![GitHub stars](https://img.shields.io/github/stars/etienne912/PolyTweet)](https://github.com/etienne912/PolyTweet/stargazers)
![Made With](https://img.shields.io/badge/made_with-java-white)

## Authors

- 👤 **Lucas Hervouet**
- 👤 **Etienne Lécrivain**

## 🚀 Usage

### Launch interface

Open terminal and execute this command:
- On Linux:

```sh
./gradlew run
```

- On Windows:
```sh
./gradlew.bat run
```

### Run test

Open terminal and execute this command:
- On Linux:
```sh
./gradlew test
```

- On Windows:
```sh
./gradlew.bat test
```

### Application

1. Click on `register` to create a new account.
    - Enter your firstname and lastname.
    - Enter your IP address.
    - _Optional : Enter the IP of another user in the `Network IP address` field to join his network._
    - Click on 'Validate'.
    
2. Thanks to the navigation bar, you can access to :
    - your personal profile,
    - the actualities,
    - your settings.
    
3. Your profile page contains all your information and posts. You can access to a list of all users you follow by clicking on the number of followed profiles.
   _Warning : if the users are not connected or if you are not in the same network, they will not be displayed._
    
4. The actualities regroup the posts of all users you follow. You can also create a new post.
   
5. The settings allow you to update your information and join / remove a neighbor.
   
6. To follow users, search them by name to get all users in your network corresponding to your search.

7. When you quit the application or log out, your profile is serialized in the `profiles` folder.

8. To log in with an existing profile, import your deserialized profile and enter your IP address.
_Optional : Enter the IP of another user in the `Network IP address` field to join his network._

## 📝 License

This project is [GNU AGPLv3](https://github.com/etienne912/PolyTweet/blob/master/LICENSE) licensed.
