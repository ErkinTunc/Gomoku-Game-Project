# UML — Model Layer

Back to main README: [README](../README.md)

This document contains the UML class diagram for the `model/` package only.

The diagram focuses on the core domain logic of Gomoku:
- Board representation
- Pieces and directions
- Player abstraction
- Rule enforcement relationships

---

## Diagram (PNG)

![Model UML](../rapport/img/gomoku-UML.png)

---

## Diagram (PDF)

For a zoom-friendly version:

- `../rapport/img/gomoku-UML.pdf`

---

## What This Diagram Shows

- Relationships between `Grid`, `Piece`, `Direction`
- Player hierarchy (`Player`, `Human`, etc.)
- Core game-domain responsibilities
- Associations and dependencies inside the model layer



## What This Diagram Does NOT Show

- `app/` orchestration layer
- `search/` engine layer
- Persistence (`save/`)
- Utility helpers (`util/`)

Those layers are intentionally excluded to keep the model diagram focused and readable.

---

## Design Intent of the Model Layer

The `model/` package represents:

- Pure domain logic
- Rule enforcement
- Board state manipulation

It contains no UI logic and no search algorithm code.