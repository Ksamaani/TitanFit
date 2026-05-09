# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## IMPORTANT RULES

1. **DO NOT modify any existing source code** — not even a single character, even if a simpler or better way to write it is found.
2. **DO NOT push to GitHub** under any circumstances.
3. **DO NOT change any backend original code** — no refactoring, no reformatting, no edits of any kind to existing `.java` files.

## Build & Run

This is a plain Java project managed by IntelliJ IDEA (no Maven/Gradle).

**Compile from project root:**
```
javac -d out/production src/*.java
```

**Run:**
```
java -cp out/production Main
```

**In IntelliJ:** Build → Build Project (Ctrl+F9), then run `Main`.

There is no automated test suite — testing is manual through the interactive console menu.

## Architecture

TitanFit is a console-based gym management system. The entry point is `src/Main.java`, which prompts the user to configure a `Gym` instance and then drives an 8-option menu loop.

### Inheritance & Interface Hierarchy

```
Person (abstract)
├── Member    (implements Payable)  — 250 SAR/month base fee
│   └── PremiumMember              — +1000 SAR for trainer, +500 SAR for spa
└── Trainer   (implements Payable) — paid monthly salary
```

`Payable` declares `makePayment()`. Each concrete class implements `calculateFee()` to compute its own amount.

### Data Storage

`Gym` holds two `List<Member>` and `List<Trainer>` instances — both are **custom generic singly-linked lists** defined in `src/List.java` and `src/Node.java`. The standard Java Collections API is intentionally not used. All add/remove/search operations go through these structures.

`PremiumMember` owns a `WorkoutPlan`, which itself contains a `List<Exercise>`.

### Key Behavioral Rules

- Member and trainer counts are capped at the limits set during `Gym` construction.
- IDs must be unique — `Gym` checks for duplicates before inserting.
- Search (`Gym.searchMember`, `Gym.searchTrainer`, `WorkoutPlan.searchExercise`) is implemented **recursively** by traversing nodes.
- `Gym.removeMember` and `WorkoutPlan.removeExercise` do direct node pointer manipulation, not list-API calls.
- Only `PremiumMember` can have a workout plan; `Main.java` uses `instanceof` to gate that menu path.

### Fee Calculation

| Entity | Fee |
|---|---|
| Member | 250 SAR/month |
| PremiumMember (base) | 250 SAR |
| + personal trainer | +1000 SAR |
| + spa access | +500 SAR |
| Trainer | monthly salary (set at creation) |
