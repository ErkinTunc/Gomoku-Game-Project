# Gomoku

**Java Implementation with Search-Oriented Architecture**

Terminal Gomoku with a clean domain model and an isolated adversarial search layer.

Gomoku is a two-player, zero-sum board game where players alternate placing stones on a grid, aiming to align five consecutive pieces horizontally, vertically, or diagonally.

Core architectural principles:

- Clear domain modeling (`model/`)
- Immutable search state design (`search/`)
- Evaluation abstraction
- Isolation of UI, persistence, and engine concerns

---

## Overview

The repository is structured around two clear layers:

### 1. Game Layer

Implements the playable version of Gomoku.

- Rule enforcement
- Win detection (horizontal, vertical, diagonal)
- Dynamic grid expansion
- Terminal interface
- Serialization-based persistence
- Player abstraction with `Human` and `AIPlayer` implementations

### 2. Search Layer (Engine Foundations)

Isolated package intended for adversarial search algorithms.

### Implemented

- Immutable `GameState`
- `Move` abstraction
- `PlayerType` (MAX / MIN semantics)
- `Evaluator` interface for heuristic scoring
- `GameStateFactory` bridging model → search representation

### Planned

- Depth-limited Minimax
- Alpha-Beta pruning
- Move ordering
- Benchmarking tools
- Unit tests for search correctness

---

## Architecture

The project enforces separation of concerns:

- `app/` — orchestration and game loop
- `model/` — core game domain
- `ai/` — baseline gameplay AI
- `save/` — persistence layer
- `util/` — console utilities
- `search/` — adversarial search layer

Design principles applied:

- Clear package boundaries
- Immutable search state preparation
- Search logic isolated from UI
- Evaluation abstraction for extensibility

## Documentation

### UML

Model layer class diagram.  
[View UML](docs/UML.md)

### Project Structure

Folder layout and responsibilities.  
[View Structure](docs/PROJECT_STRUCTURE.md)

### Architecture

Design decisions and dependency rules.  
[Read Architecture Notes](docs/ARCHITECTURE.md)

---

## Why This Structure Matters

The architecture allows:

- Independent evolution of search algorithms
- Safe recursive exploration (immutability)
- Replacement of UI without touching engine logic
- Unit testing of search components in isolation

The system is designed to support adversarial search algorithms without restructuring the gameplay layer.

---

## Run

### Using Scripts

| Platform    | Run Application  | Generate Javadoc    |
| ----------- | ---------------- | ------------------- |
| Windows     | `runGomoku.bat`  | `generateDocs.bat`  |
| Linux/macOS | `./runGomoku.sh` | `./generateDocs.sh` |

### Manual Compilation

```bash
javac -d target/classes src/main/java/*/*.java
java -cp target/classes app.Gomoku
```

---

## Author

Erkin Tunc Boya
