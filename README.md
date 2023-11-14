# Projet Système Distribué 2023

## Description
L'objectif de ce projet est de vous permettre de mettre en oeuvre différents aspects de la conception et du développement d'une application distribuée. Vous allez construire une application de plus en plus complexe, en utilisant différentes technologies. Vous utiliserez toujours le même projet, mais vous allez le modifier au fur et à mesure des étapes. Chaque étape sera taggée, afin de pouvoir vérifier votre travail. Ce projet vous permettra d'acquérir différentes notions et compétences au fur et à mesure. Différentes documentations vous permettront de vous familiariser avec les technologies utilisées.

## Étapes

### Étape 1: Appeler une API REST d'un service externe - création de composants et injection de dépendances

Nous allons utiliser OpenWeatherMap comme source d'informations météorologiques. Pour cela, vous devez créer un compte sur le site https://openweathermap.org/ et récupérer votre clé d'API. Vous pouvez utiliser la documentation de l'API pour comprendre comment l'utiliser.

1. Créez une classe qui permet de faire une requête permettant de récupérer les informations de météo d'une ville. Vous pouvez utiliser la librairie `Gson` pour parser le JSON retourné par l'API. Vous pouvez combiner votre application avec le service de Geocoding pour retrouver les coordonnées géographiques d'un lieu. [CALL]

2. Ecrivez des tests unitaires pour votre classe. Vous pouvez utiliser JUnit ou TestNG. Vous vérifierez que votre classe retourne bien les informations météorologiques d'une ville si elle existe et que votre classe retourne une erreur si la ville n'existe pas. [TESTCALL]

### Étape 2: Création d'une base de données et mise en place de composants. 

Pour cette partie vous vous référerez au cours sur la gestion de la persistence et sur l'injection de dépendances.

1. Créez un composant Spring `WeatherComponent` permettant d'appeler les services d'OpenWeatherMap. [WEATHERCOMPONENT]

2. Créez un autre composant Spring `WeatherStore`permettant de rendre persistant les résultats des appels au service de météo. Vous utiliserez Spring Data JPA pour accéder à la base de données. Vous utiliserez une base de données relationnelle (MariaDB). Vous pouvez utiliser Docker pour installer et exécuter. Faites en sorte d'avoir un schéma en 3NF sur la base des données du service de météo. [PERSISTENCE]

Attention, pour cette partie, il y a beaucoup de choses à faire. Je vous conseille de commencer par créer une base de données et de créer une classe qui permet de faire des requêtes simples sur la base de données. Vous pourrez ensuite créer un composant qui permet de rendre persistant les résultats de chaque appel au service de météo.

3. Ecrivez des requêtes permettant de rechercher des informations météo par région, par ville, par date, par intervalle de date, etc. Vous pouvez utiliser Spring Data JPA pour accéder à la base de données. [REQUETES]

### Étape 3: Création d'un composant métier

On va maintenant passer à la dimension service de l'application. Vous devez transformer votre composant principal en service, toujours en utilisant Spring. Vous pouvez utiliser l'injection de dépendances pour injecter les composants nécessaires. Vous pouvez utiliser les annotations `@Service` et `@Autowired` pour déclarer vos services et injecter les dépendances. 


1. Créez une classe `WeatherService` qui contient une méthode `getWeather(String city)` qui retourne un objet `Weather` contenant les informations météorologiques de la ville passée en paramètre. Vous pouvez utiliser la librairie `Gson` pour parser le JSON retourné par l'API. [SERVICE]. Cette classe utilisera les composants de la partie précédente.


2. On cherche maintenant à faire un service Météo spécialisé dans les villes de Lorraine. Vous ajouterez un autre service `VilleDeLorraineService` permettant de vérifier que la commune proposée est bien en Lorraine. Les données sont fournies par cette API:

https://datanova.laposte.fr/api/records/1.0/search/?dataset=georef-france-commune&q=&sort=year&facet=year&facet=reg_name&facet=dep_name&facet=arrdep_name&facet=ze2020_name&facet=bv2012_name&facet=epci_name&facet=ept_name&facet=com_name&facet=com_area_code&facet=com_type&facet=ze2010_name&facet=com_is_mountain_area&refine.reg_name=Grand+Est

[LORRAINE]

3. Vous combinerez ces services pour créer une application qui permet de récupérer les informations météorologiques d'une ville de Lorraine. [METEOLORRAINE]

### Etape 4: Création d'une API REST
1. Créez une API REST qui permet de récupérer les informations météorologiques d'une ville de Lorraine. Vous pouvez utiliser Spring Boot pour créer votre API. Vous pouvez utiliser Swagger pour documenter votre API. Vous créerez un `WeatherController` qui fera appel au `WeatherService` et au `VilleDeLorraineService`. Vous identifierez les différents endpoints de votre API.
[REST]

2. Ecrivez des tests unitaires pour votre API. Vous serez peut être amené à utiliser des mocks pour vos tests. 
[RESTTEST]

### Étape 4: Création d'un service d'alerte météo
On veut prévenir des utilisateurs ou des services en fonction de la température à certains endroits. Pour cela, les utilisateurs pourront enregistrer des alertes. Les alertes sont définies par un lieu et une température. Si la température est supérieure ou inférieure à la température définie, alors une alerte est déclenchée.
Le service d'alerte sera consulté chaque fois qu'une nouvelle donnée météorologique est enregistrée dans la base de données. Si une alerte est déclenchée, alors un message sera placé dans une queue RabbitMQ. Le service d'alerte est un service REST qui permet de créer, modifier, supprimer des alertes. Il permet aussi de consulter les alertes déclenchées.
[RABBITMQ]

### Etape 4bis: Création d'un front. 
Vous allez créer un front qui permettra de récupérer les informations météorologiques d'une ville de Lorraine. Vous pouvez utiliser React ou Angular ou faire un simple front en Python en utilisant Flask.
[FRONT]

### Etape 5: Ajout d'un service de cache

Le service de cache permettra aux utilisateurs de récupérer les données météorologiques sans avoir à appeler l'API externe d'OpenWeatherMap. Le service de cache doit être mis à jour à chaque fois qu'une donnée est enregistrée dans la base de données. 
 Le service de cache permettra également de contrôler le nombre de requêtes faites à l'API externe. Pour une ville donnée, on ne mettra pas à jour le cache si la dernière mise à jour date de moins de 60 minutes. Si la dernière mise à jour date de moins de 60 minutes, on retournera la valeur du cache. Si la dernière mise à jour date de plus de 60 minutes, on mettra à jour le cache et on retournera la valeur du cache. 
* La limite du nombre d'appel par jour est de 1000. Si le nombre d'appel est dépassé, on retournera la valeur du cache. S'il n'y a pas de valeur dans le cache pour la ville demandée, on retournera la valeur de la ville la plus proche à vol d'oiseau à partir des coordonnées GPS qui dispose d'une valeur de température. 
* Vous utiliserez Redis pour implémenter le cache.
* Vous utiliserez Spring Data Redis pour accéder à Redis.
[CACHE]

### Etape 6: Déploiement avec Docker-Compose
Votre application comporte plusieurs micro services (Weather, Lorraine, Alerte), une ou deux bases de données, un service de cache, un service de messagerie. Vous allez créer un fichier docker-compose.yml qui permettra de déployer votre application simplement.
[DOCKER]

### Etape 7: API Gateway et Authentification
Vous allez créer un API Gateway qui permettra de gérer l'authentification des utilisateurs. 
* Vous utiliserez Spring Cloud Gateway. 
* Vous utiliserez Spring Security pour gérer l'authentification des utilisateurs. 
* Vous utiliserez Spring Cloud Config pour gérer la configuration de votre application. 
* Vous utiliserez Spring Cloud Discovery pour gérer la découverte des services.
* Vous utiliserez Spring Cloud Circuit Breaker pour gérer les appels aux services.
[GATEWAY]


### Etape 8: Déploiement dans le Cloud

Si on a le temps.



