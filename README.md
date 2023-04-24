# Dezentrale Hacker:innen-Events
## Projektbeschreibung

Wir wollen eine App erstellen, die es ermöglicht einerseits Events zu pflegen und andererseits wieder auszulesen. Jedes Event hat ein Start- und ein Enddatum, einen Titel und eine Beschreibung. Außerdem hat jedes Event eine:n Hacker:in als Verantwortliche:n. Der Eventkalender soll über das Internet erreichbar sein.

## Funktionale Anforderungen

Wir möchten eine möglichst hohe Abdeckung mit automatischen Tests haben, Stichwort: code coverage. Der Kalender soll per RSS-Feed oder als ICAL abonnierbar sein.

## Technologien die wir gern verwenden wollen

- eine relationale Datenbank (bspw. PostgreSQL)
- Security per JSON Web Token (JWT), bespw. Keycloak
- Kotlin fürs Backend
- ein reaktives Frontend ist optional

## PostgreSQL in Docker

Bevor Ihr dieses oder ein anderes Projekt in Docker ausführt, empfehle ich [Run the Docker daemon as a non-root user (Rootless mode)](https://docs.docker.com/engine/security/rootless/) zu lesen. Das funktioniert mittlerweile super, mit den zur Verfügung gestellten Skripten. Hintergrund ist, dass unter Windows oder Mac-OS Docker immer in einer VM ausgeführt wird, was unter Unix-artigen Betriebssystemen nicht der Fall ist. Bspw. schreibt die postgres-DB Daten unter `.tmp/db` mit root-Rechten auf die Festplatte, was er im Rootless-Scenario als der User macht, mit dem man angemeldet ist.

Um das aktuelle Projekt auszuführen und lokal auszuprobieren, einfach:

```shell
docker compose up -d
```

aufrufen.