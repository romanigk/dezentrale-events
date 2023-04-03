# Dezentrale Hacker-Events
## Projektbeschreibung

Wir wollen eine App erstellen, die es ermöglicht einerseits Events zu pflegen und andererseits wieder auszulesen. Jedes Event hat ein Start- und ein Enddatum, einen Titel und eine Beschreibung. Außerdem hat jedes Event eine:n Hacker:in als Verantwortliche:n. Der Eventkalender soll über das Internet erreichbar sein.

## Funktionale Anforderungen

Wir möchten eine möglichst hohe Abdeckung mit automatischen Tests haben, Stichwort: code coverage. Der Kalender soll per RSS-Feed oder als ICAL abonnierbar sein.

## Technologien die wir gern verwenden wollen

- eine relationale Datenbank (bspw. PostgreSQL)
- Security per JSON Web Token (JWT), bespw. Keycloak
- Kotlin fürs Backend
- ein reaktives Frontend ist optional
