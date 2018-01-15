# Ticketoffice

Test application for Moneta Bank by Maksym Prysiazhnyi

## Getting Started

"Napište program, který bude vystavovat REST rozhraní pro ticketovací systém na pobočce. Ten by měl vystavovat následující REST služby:
- Vygenerování pořadového čísla, s tím že vracet bude navíc ještě datum a čas vygenerování a pořadí ve frontě
- Získání aktuálního aktivního čekajícího čísla
- Smazání posledního aktivního čísla
 
Například:
V seznamu aktivních ticketů bude: 
- 1245, 2017-09-01 15:22, 0
- 1246, 2017-09-01 15:42, 1
- 1250, 2017-09-01 16:32, 2
 
Vygenerování nového vrátí:
- 1251, 2017-09-01 19:20, 3
 
Získání aktuálního vrátí:
- 1245, 2017-09-01 15:22, 0
 
Po smazání posledního bude v seznamu:
- 1246, 2017-09-01 15:42, 0
- 1250, 2017-09-01 16:32, 1
- 1251, 2017-09-01 19:20, 2
 
Úloha by měla být na cca 1-2 hodin."

### Prerequisites

Install JDK

### Endpoints

- GET /           - Retrieve a list of tickets
- GET /generate   - Generate a new ticket
- GET /useActive  - Use active ticket, so it is removed after
- GET /removeLast - Remove last ticket from queue

## Running the tests

Run the tests with your IDE

## Authors

* **Maksym Prysiazhnyi** - *Initial work*
