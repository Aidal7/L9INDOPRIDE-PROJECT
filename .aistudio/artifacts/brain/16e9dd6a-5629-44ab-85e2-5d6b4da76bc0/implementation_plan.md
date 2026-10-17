# Lord Nine MMORPG Guide - Item Detail Modal & Catalog

An interactive native Android guide application for Lord Nine MMORPG featuring an authentic dark MMORPG aesthetic (Obsidian #0B0D12, Dark Slate #1A1E29, and Gold #D4AF37), backed by an embedded SQLite database (`l9.db`) with item stats, grade systems, and monster drop sources.

## User Review & Critical Decisions

> [!IMPORTANT]
> The following architectural decisions were confirmed during interactive clarification:
> - **Framework Implementation**: Native Android Jetpack Compose in this app with high-fidelity UI components, accompanied by a copyable/exportable Flutter/Dart widget code sheet in-app so you can directly use the Dart snippet in external Flutter projects if needed.
> - **Data Persistence**: Embedded SQLite database (`l9.db` schema via Android Room) pre-populated with verified Lord Nine items, base attributes, weapon types, and monster drop tables.
> - **Modal Presentation**: Interactive M3 Modal Bottom Sheet with drag handle, responsive height, item stats grid, and monster drop location list.

---

### 1. Overview & Core Concept

- **What It Does**: Provides a dedicated item browser and detailed item inspection modal for Lord Nine players. Selecting any weapon or equipment launches a high-fidelity modal displaying the item icon, dynamic grade border (Common to Mythic), weapon category, complete base stat table, and monster drop locations with spawn maps and drop rates.
- **Target Audience**: Lord Nine MMORPG players, theorycrafters, and guild farmers looking for drop tables and item statistics.
- **Key Value**: Instant offline access to weapon stats and drop sources without switching apps or searching complex web tables.

---

### 2. User Experience & Visual Design

#### Visual Identity & Theme Palette
- **Obsidian Background**: `#0B0D12` (deep atmospheric MMORPG dark canvas).
- **Dark Slate Surface**: `#1A1E29` (elevated card backgrounds, bottom sheet container).
- **Gold Accents & Borders**: `#D4AF37` (ornate gold borders, headers, and badge trims).
- **Item Grade Rarity Colors**:
  - **Common**: Gray `#9E9E9E` / `#2C3038`
  - **Uncommon**: Green `#4CAF50` / `#1B3820`
  - **Rare**: Blue `#2196F3` / `#142C48`
  - **Epic**: Purple `#9C27B0` / `#36174D`
  - **Legendary**: Orange / Gold `#FF9800` / `#4A2A08`
  - **Mythic**: Crimson Red `#E91E63` / `#FF1744` with shimmering border highlights

#### Key User Flows
1. **Catalog Screen**: Browse weapons with real-time search, filter chips by weapon type (Bow, Greatsword, Dagger, Staff, One-Handed Sword, Crossbow), and rarity filter.
2. **Item Modal Trigger**: Tapping any item card animates an M3 `ModalBottomSheet` styled with Dark Slate (`#1A1E29`), Obsidian interior (`#0B0D12`), and Gold Border (`#D4AF37`).
3. **Modal Content Layout**:
   - **Header Section**: Left item icon with rarity border frame, right header with item name, rarity badge, weapon type, and required level.
   - **Stats Table**: Structured 2-column key-value grid (Attack, Crit Rate, Accuracy, Attack Speed, Penetration, Weight).
   - **Monster Drop Table**: List of monsters dropping the item, with monster level, spawn map/dungeon, and drop rate percentage.
   - **Flutter Code Export Tab**: A dedicated toggle to view and copy the clean Flutter/Dart widget code matching the user's initial request.

---

### 3. Key Product Decisions & Trade-Offs

- **Decision 1: Room Database with Asset Prepopulation (`l9.db`)**
  - *Chosen Approach*: Use Android Room Database bundled with initial Lord Nine weapon and drop table records.
  - *Why*: Provides reactive Kotlin `Flow`, rock-solid offline queries, and clean separation between UI and SQLite database.
- **Decision 2: Modal Bottom Sheet with Drag & Scroll Handling**
  - *Chosen Approach*: Jetpack Compose `ModalBottomSheet` with nested scrolling support for both stats and monster drops.
  - *Why*: Standard Android ergonomic gesture pattern for one-handed mobile use while accommodating varying screen heights.
- **Decision 3: Dual Native Jetpack Compose + Flutter/Dart Source Code Export**
  - *Chosen Approach*: Build the full interactive UI in native Jetpack Compose while providing a dedicated "Flutter Code" viewer dialog/tab with the exact Dart code requested.
  - *Why*: Delivers an immediate runnable app preview on the emulator while directly fulfilling the user's Flutter/Dart snippet requirement.

---

### 4. Technical Architecture & Data Strategy

```
┌─────────────────────────────────────────────────────────────┐
│                    MainActivity (Scaffold)                  │
├──────────────────────────────┬──────────────────────────────┤
│       Search & Filters       │     Catalog List (LazyColumn)│
└──────────────────────────────┴──────────────────────────────┘
                               │ (Click Item)
                               ▼
┌─────────────────────────────────────────────────────────────┐
│            ItemDetailBottomSheet (Modal Sheet)              │
├─────────────────────────────────────────────────────────────┤
│ ┌────────────────────────┐  ┌─────────────────────────────┐ │
│ │ Item Icon Frame        │  │ Item Name                   │ │
│ │ (Dynamic Grade Border) │  │ Grade Badge & Weapon Type   │ │
│ └────────────────────────┘  └─────────────────────────────┘ │
│ ┌─────────────────────────────────────────────────────────┐ │
│ │ Base Stats Table (Card: Dark Slate + Gold Border)       │ │
│ └─────────────────────────────────────────────────────────┘ │
│ ┌─────────────────────────────────────────────────────────┐ │
│ │ Monster Drop Locations (Monster, Map, Drop Rate %)      │ │
│ └─────────────────────────────────────────────────────────┘ │
│ ┌─────────────────────────────────────────────────────────┐ │
│ │ Action Bar: "View Flutter / Dart Code" + "Share / Close"│ │
│ └─────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
                               ▲
                               │
┌──────────────────────────────┴──────────────────────────────┐
│                    ItemViewModel (StateFlow)                │
├─────────────────────────────────────────────────────────────┤
│                 ItemRepository (Data Access)                │
├─────────────────────────────────────────────────────────────┤
│                AppDatabase (Room SQLite / l9.db)            │
│  - ItemEntity (id, name, grade, type, icon, stats)          │
│  - MonsterDropEntity (id, itemId, monsterName, map, rate)   │
└─────────────────────────────────────────────────────────────┘
```

#### Database Entities
- **ItemEntity**:
  - `id: String` (e.g. `"wpn_1001"`)
  - `name: String` (e.g. `"Dragon Slayer Greatsword"`, `"Nerucas Bow"`)
  - `grade: String` (`Common`, `Uncommon`, `Rare`, `Epic`, `Legendary`, `Mythic`)
  - `weaponType: String` (`Greatsword`, `Bow`, `Staff`, `Dagger`, `Sword & Shield`, `Crossbow`)
  - `levelReq: Int`
  - `attackPower: Int`
  - `attackSpeed: Float`
  - `criticalRate: Float`
  - `accuracy: Int`
  - `penetration: Int`
  - `description: String`
- **MonsterDropEntity**:
  - `id: Int`
  - `itemId: String`
  - `monsterName: String`
  - `monsterLevel: Int`
  - `mapName: String`
  - `dropRatePct: String`
  - `monsterType: String` (Melee / Ranged / Magic)
