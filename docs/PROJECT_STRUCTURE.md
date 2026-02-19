# Project Structure

Back to main README: [README](../README.md)

This document explains the folder organization and design intent behind the project layout.

---

## Top-Level Structure

```
Gomoku-Game-Projet/
├── .vscode/                      # Optional VSCode configs
├── Gomoku/
│   ├── generateDocs.bat          # Windows: Generate JavaDoc
│   ├── generateDocs.sh           # Linux/Mac: Generate JavaDoc
│   ├── runGomoku.bat             # Windows: Compile and Run Gomoku
│   ├── runGomoku.sh              # Linux/Mac: Compile and Run Gomoku
│   ├── pom.xml                   # Optional Maven configuration
│   ├── data/                     # Saved game files (.dat)
│   ├── src/
│   │   ├── main/
│   │   │   └── java/
│   │   │       ├── app/
│   │   │       ├── model/
│   │   │       ├── ai/
│   │   │       ├── save/
│   │   │       ├── util/
│   │   │       └── search/
│   │   └── test/
│   │       └── java/
│   └── target/
│       ├── classes/
│       └── test-classes/
├── docs/                         # Technical documentation (UML, structure, roadmap)
├── rapport/                      # Final report LaTeX source
│   └── rapport.tex
├── README.md
└── .gitignore
```

---

## Package-Level Breakdown

### `app/`

Application entry point and orchestration layer.  
Owns the game loop and connects players to the engine.  
Contains no domain rules or search logic.

### `model/`

Core game domain.  
Implements grid management, move validation, win detection, and player abstractions.  
Pure game logic with no UI or search dependencies.

### `ai/`

Baseline opponent logic used in the playable version.  
Independent from the formal search engine.

### `search/`

Adversarial search layer.

Core components

- `GameState` — immutable search state
- `Move` — domain object for actions
- `PlayerType` — MAX / MIN abstraction
- `Evaluator` — heuristic evaluation interface
- `GameStateFactory` — converts model state into search representation
- (Minimax / Alpha-Beta planned here)

Design intent

- Clean separation between:
  - Board mechanics (`model`)
  - Search algorithms (`search`)
- Safe recursive exploration through immutability
- Easy future extension (Minimax, Alpha-Beta, benchmarks)

### `save/`

Serialization-based persistence.

### `util/`

Console formatting and helper utilities.

---

## Supporting Directories

- `data/` — saved games
- `target/` — compiled classes
- `rapport/` — academic report source
- `docs/` — UML, architecture, structure, roadmap

---

## Architectural Principles Applied

- Separation of concerns
- Single responsibility per package
- Search logic isolated from UI
- Immutable state prepared for recursive algorithms
- Clear domain modeling

---

## Why This Structure Matters

This layout allows:

- Independent evolution of search algorithms
- Replacement of UI without touching engine
- Unit testing of search without terminal interaction
- Clear reasoning about dependencies

The repository layout supports independent evolution of the search engine.
