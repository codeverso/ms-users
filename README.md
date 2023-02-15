# Users Microservice
![last_commit](https://img.shields.io/github/last-commit/codeverso/ms-users) 

## Spring Boot, Flyway, Postgres, Docker and MockMvc Rest API Tutorial

Restful CRUD API for a simple Goals microservice using Spring Boot, Postgres, Docker and MockMvc.

<div id="header" align="left">
  <img src="https://user-images.githubusercontent.com/25549745/216848518-bc074cd3-892a-4a7d-934b-7d1f6eb5f2ec.png" width="300"/>
</div>


Microservice used for youtube videos tutorials.

## How to run

Go into the repository you just cloned:\
`cd ms-users`

Build and download all the dependencies from the application:\
`mvn clean install`

Build the dockerfile to create the image's application:\
`docker build -t codeverso .`

Run the docker-compose file:\
`docker-compose -f src/main/resources/docker-compose/docker-compose.yml up -d`

To check if the Postgres and Codeverso images are running using the command:\
`docker ps`

If everything is working good, you can call the endpoints from the container through the url:\
`http://localhost:8082/users`


## Contributors ✨

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tr>
    <td align="center"><a href="https://www.linkedin.com/in/gabrielbabler/"><img src="https://avatars.githubusercontent.com/u/20668748?v=4?s=100" width="100px;" alt=""/><br /><sub><b>Gabriel Babler</b></sub></a><br /><a href="https://github.com/codeverso/ms-users/commits?author=gabrielbabler" title="Dev">💻</a></td>
    <td align="center"><a href="https://www.linkedin.com/in/murillowelsi/"><img src="https://avatars.githubusercontent.com/u/25549745?v=4?s=100" width="100px;" alt=""/><br /><sub><b>Murillo Welsi</b></sub></a><br /><a href="https://github.com/codeverso/ms-users/commits?author=murillowelsi" title="QA">🧪</a></td>
    <td align="center"><a href="https://www.linkedin.com/in/alexandrepansan/"><img src="https://avatars.githubusercontent.com/u/31599244?v=4?s=100" width="100px;" alt=""/><br /><sub><b>Alexandre Pansan Jr.</b></sub></a><br /><a href="https://github.com/codeverso/ms-users/commits?author=AllePansan" title="Dev">💻</a></td>
    </tr>
</table>
<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->
<!-- ALL-CONTRIBUTORS-LIST:END -->
