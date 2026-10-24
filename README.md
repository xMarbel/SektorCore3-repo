# SektorCore3

Partia 3 pluginu na sektory (Paper 1.21.4, Java 21).

## Co jest
- `/pl`, `/plugins`, `/pluginy` - ladna wiadomosc o serwerze + klikalny Discord (edytowalna w `config.yml`).
  Admini (`sektor.admin`) widza prawdziwa liste pod `/bukkit:pl`.

## Budowanie
Kazdy push buduje jar w GitHub Actions: zakladka **Actions** -> ostatni run -> **Artifacts** -> `SektorCore3`.
Lokalnie: `mvn package` (jar w `target/`).
