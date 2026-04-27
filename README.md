# Admin Mod

Ein Fabric-Mod für **Minecraft 1.21.11**, der konfigurierten Spielern erlaubt, OP-Befehle auszuführen, **ohne** in der `ops.json` zu stehen. Welche Befehle erlaubt sind, lässt sich frei einstellen.

## Features

- Spieler können per UUID oder Name als „Admin" eingetragen werden
- Admins erhalten Operator-Berechtigungsstufe für Befehle (Permission Level 4)
- Die erlaubten Befehle sind frei konfigurierbar (Whitelist)
- Leere `allowedCommands`-Liste = **alle** Befehle erlaubt
- In-Game Befehl `/adminmod reload` zum Neu-Laden der Config
- In-Game Befehl `/adminmod info` zum Anzeigen der aktuellen Einstellungen

## Installation

1. [Fabric Loader 0.16.10+](https://fabricmc.net/use/installer/) für Minecraft 1.21.11 installieren
2. [Fabric API 0.139.4+1.21.11](https://modrinth.com/mod/fabric-api) in den `mods`-Ordner legen
3. `admin-mod-1.0.0.jar` in den `mods`-Ordner legen
4. Server starten – die Konfigurationsdatei wird automatisch erstellt

## Konfiguration

Die Konfigurationsdatei liegt unter `config/admin-mod.json` und wird beim ersten Start automatisch erstellt:

```json
{
  "admins": [
    "550e8400-e29b-41d4-a716-446655440000",
    "Steve"
  ],
  "allowedCommands": [
    "gamemode",
    "give",
    "tp",
    "time",
    "weather",
    "effect",
    "kill"
  ]
}
```

### Felder

| Feld              | Beschreibung                                                                            |
|-------------------|-----------------------------------------------------------------------------------------|
| `admins`          | Liste von Spieler-UUIDs oder Namen. UUID bevorzugt (robust bei Namensänderungen).       |
| `allowedCommands` | Liste erlaubter Root-Befehle (ohne `/`). **Leer = alle Befehle erlaubt.**               |

### UUID herausfinden

```
/data get entity <Spielername> UUID
```
oder auf [mcuuid.net](https://mcuuid.net/) nachschlagen.

### Config neu laden (ohne Serverneustart)

```
/adminmod reload
```

## Befehle

| Befehl             | Beschreibung                          | Voraussetzung        |
|--------------------|---------------------------------------|----------------------|
| `/adminmod reload` | Konfiguration neu laden               | Admin oder echter OP |
| `/adminmod info`   | Aktuelle Admins und Befehle anzeigen  | Admin oder echter OP |

## Build

Voraussetzungen: Java 21, Gradle 8.8

```bash
# Gradle-Wrapper initialisieren (einmalig, falls noch nicht vorhanden)
gradle wrapper --gradle-version 8.8

# Mod bauen
./gradlew build
# Die fertige JAR liegt in: build/libs/admin-mod-1.0.0.jar
```

## Versionen

| Komponente    | Version              |
|---------------|----------------------|
| Minecraft     | 1.21.11              |
| Fabric Loader | 0.16.10              |
| Fabric API    | 0.139.4+1.21.11      |
| Yarn Mappings | 1.21.11+build.3      |
| Java          | 21                   |
