# Architecture Notes

Back to main README: [README](../README.md)

This document explains the architectural choices of the project and the intent behind the package boundaries.

---

## High-Level Design

The repository is organized into two layers:

1. **Game Layer**: implements Gomoku as a playable terminal game.
2. **Search Layer**: isolates adversarial search concerns in a separate package.

The main architectural goal is to keep gameplay concerns (UI, menus, persistence) separate from search concerns (state representation, move generation, evaluation, search algorithms).

---

## Package Responsibilities

### `app/` — Orchestration

**Role:** coordinates execution and user interaction.

- Owns the game loop and menus
- Connects players to the game engine
- Calls into the model layer and persistence layer

**Rule:** no search logic and no domain rules should be implemented here.

### `model/` — Domain Logic

**Role:** core rules and entities of Gomoku.

- Grid representation and updates
- Piece placement logic
- Win detection
- Player abstractions used by the playable game

**Rule:** no UI concerns, no persistence logic, no adversarial search logic.

### `ai/` — Baseline Gameplay AI

**Role:** a playable AI opponent for the terminal game.

- Heuristic move selection intended for gameplay, not exhaustive search
- Can be replaced by the search engine later

**Rule:** does not define the formal search model. Avoid mixing with `search/`.

### `save/` — Persistence

**Role:** serialization-based save/load.

- Save `.dat` snapshots
- Load saved games back into a playable state

**Rule:** persistence is an infrastructure concern; it should not contain game rules or AI logic.

### `util/` — Utilities

**Role:** generic helpers (console formatting, etc.)

**Rule:** utilities should remain dependency-free and not pull in model/search logic.

### `search/` — Adversarial Search Layer

**Role:** an isolated engine designed for Minimax / Alpha-Beta.

Core elements:

- `GameState` — immutable search state
- `Move` — action representation
- `PlayerType` — MAX/MIN semantics
- `Evaluator` — heuristic evaluation API
- `GameStateFactory` — bridge from gameplay model to search representation
- `SearchResult` — typed return container for `(bestMove, score)`

**Rule:** `search/` must not depend on terminal UI. It should be runnable and testable without `app/`.

---

## Key Design Decisions

### Immutable Search State

The `search.GameState` is designed to be immutable.

**Why:**
Recursive algorithms (Minimax, Alpha-Beta) frequently branch states. If the state is mutated in-place, subtle bugs appear:

- siblings corrupt each other’s boards
- rollback logic becomes fragile
- bugs become non-reproducible

Immutability makes search:

- easier to reason about
- safer to refactor
- simpler to test

---

## Model → Search Boundary (Factory)

`GameStateFactory` converts the gameplay representation (`model/`) into the search representation (`search/`).

**Why:**

- prevents search code from depending on internal model details
- allows the game layer to evolve without rewriting the engine
- enables lightweight representations in `search/` (compact arrays, optimized access)

---

## Evaluation as a Pluggable Component

The heuristic scoring is isolated behind `Evaluator`.

**Why:**

- keeps Minimax generic
- makes heuristics independently testable
- supports future strategy tuning (multiple evaluators)

---

## Dependency Rules (Enforced by Convention)

The intended dependency direction is:

- `app/` → `model/`, `ai/`, `save/`, `search/`
- `ai/` → `model/`
- `save/` → `model/` (and data objects needed for persistence)
- `search/` → should stay independent from terminal UI

The search engine should be callable from the game loop, but should not know that a terminal UI exists.

---

## Evolution Path

The architecture is designed to support the next steps cleanly:

- depth-limited Minimax implementation in `search/`
- Alpha-Beta pruning as a drop-in extension
- move ordering strategy as a separate component
- benchmarking harness measuring nodes explored + time
- unit testing of:
  - terminal detection
  - evaluation consistency
  - minimax correctness
  - alpha-beta equivalence

---

## Architectural Intent

The project is structured for extension rather than completion.
Search algorithms, evaluation strategies, and benchmarking tools
can evolve independently without modifying gameplay logic.
