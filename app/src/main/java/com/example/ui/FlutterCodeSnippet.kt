package com.example.ui

object FlutterCodeSnippet {
    val DART_CODE = """
import 'package:flutter/material.dart';
import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart' as p;

/// Model Data Item Lord Nine
class LordNineItem {
  final String id;
  final String name;
  final String grade; // Common, Uncommon, Rare, Epic, Legendary, Mythic
  final String weaponType;
  final int levelReq;
  final int attackPower;
  final double attackSpeed;
  final double criticalRate;
  final int accuracy;
  final int penetration;
  final double weight;
  final String description;

  LordNineItem({
    required this.id,
    required this.name,
    required this.grade,
    required this.weaponType,
    required this.levelReq,
    required this.attackPower,
    required this.attackSpeed,
    required this.criticalRate,
    required this.accuracy,
    required this.penetration,
    required this.weight,
    required this.description,
  });

  factory LordNineItem.fromMap(Map<String, dynamic> map) {
    return LordNineItem(
      id: map['id']?.toString() ?? '',
      name: map['name'] ?? '',
      grade: map['grade'] ?? 'Common',
      weaponType: map['weaponType'] ?? '',
      levelReq: map['levelReq'] ?? 1,
      attackPower: map['attackPower'] ?? 0,
      attackSpeed: (map['attackSpeed'] as num?)?.toDouble() ?? 1.0,
      criticalRate: (map['criticalRate'] as num?)?.toDouble() ?? 0.0,
      accuracy: map['accuracy'] ?? 0,
      penetration: map['penetration'] ?? 0,
      weight: (map['weight'] as num?)?.toDouble() ?? 0.0,
      description: map['description'] ?? '',
    );
  }
}

/// Model Drop Monster dari l9.db
class MonsterDrop {
  final String monsterName;
  final int monsterLevel;
  final String mapName;
  final String dropRatePct;
  final String monsterType;

  MonsterDrop({
    required this.monsterName,
    required this.monsterLevel,
    required this.mapName,
    required this.dropRatePct,
    required this.monsterType,
  });

  factory MonsterDrop.fromMap(Map<String, dynamic> map) {
    return MonsterDrop(
      monsterName: map['monsterName'] ?? '',
      monsterLevel: map['monsterLevel'] ?? 1,
      mapName: map['mapName'] ?? '',
      dropRatePct: map['dropRatePct'] ?? '0.0%',
      monsterType: map['monsterType'] ?? 'Melee',
    );
  }
}

/// Service Pembaca Database SQLite l9.db
class L9DatabaseService {
  static Database? _database;

  static Future<Database> get database async {
    if (_database != null) return _database!;
    _database = await _initDb();
    return _database!;
  }

  static Future<Database> _initDb() async {
    final dbPath = await getDatabasesPath();
    final path = p.join(dbPath, 'l9.db');
    return await openDatabase(
      path,
      version: 1,
      onCreate: (db, version) async {
        // Skema tabel l9.db
        await db.execute('''
          CREATE TABLE items (
            id TEXT PRIMARY KEY,
            name TEXT,
            grade TEXT,
            weaponType TEXT,
            levelReq INTEGER,
            attackPower INTEGER,
            attackSpeed REAL,
            criticalRate REAL,
            accuracy INTEGER,
            penetration INTEGER,
            weight REAL,
            description TEXT
          )
        ''');
        await db.execute('''
          CREATE TABLE monster_drops (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            itemId TEXT,
            monsterName TEXT,
            monsterLevel INTEGER,
            mapName TEXT,
            dropRatePct TEXT,
            monsterType TEXT
          )
        ''');
      },
    );
  }

  static Future<List<MonsterDrop>> getDropsByItemId(String itemId) async {
    final db = await database;
    final res = await db.query(
      'monster_drops',
      where: 'itemId = ?',
      whereArgs: [itemId],
      orderBy: 'monsterLevel DESC',
    );
    return res.map((e) => MonsterDrop.fromMap(e)).toList();
  }
}

/// Helper Warna Berdasarkan Grade Item Lord Nine
class GradeColorHelper {
  static Color getBorderColor(String grade) {
    switch (grade.toLowerCase()) {
      case 'mythic':
        return const Color(0xFFFF1744); // Crimson Merah Terang
      case 'legendary':
        return const Color(0xFFFFA726); // Emas Jingga
      case 'epic':
        return const Color(0xFFAB47BC); // Ungu Mistis
      case 'rare':
        return const Color(0xFF2196F3); // Biru Langka
      case 'uncommon':
        return const Color(0xFF4CAF50); // Hijau
      default:
        return const Color(0xFF9E9E9E); // Abu-abu Common
    }
  }

  static Color getBackgroundColor(String grade) {
    switch (grade.toLowerCase()) {
      case 'mythic':
        return const Color(0xFF420E19);
      case 'legendary':
        return const Color(0xFF3F260C);
      case 'epic':
        return const Color(0xFF2E173E);
      case 'rare':
        return const Color(0xFF11263F);
      case 'uncommon':
        return const Color(0xFF162E19);
      default:
        return const Color(0xFF22252C);
    }
  }
}

/// Widget Bottom Sheet Modal Detail Item Lord Nine
class ItemDetailModal extends StatelessWidget {
  final LordNineItem item;

  const ItemDetailModal({Key? key, required this.item}) : super(key: key);

  static void show(BuildContext context, LordNineItem item) {
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      backgroundColor: Colors.transparent,
      builder: (ctx) => ItemDetailModal(item: item),
    );
  }

  @override
  Widget build(BuildContext context) {
    const obsidianBg = Color(0xFF0B0D12);
    const darkSlate = Color(0xFF1A1E29);
    const goldBorder = Color(0xFFD4AF37);
    const goldAccent = Color(0xFFE5C358);

    final gradeColor = GradeColorHelper.getBorderColor(item.grade);
    final gradeBgColor = GradeColorHelper.getBackgroundColor(item.grade);

    return Container(
      decoration: BoxDecoration(
        color: darkSlate,
        borderRadius: const BorderRadius.vertical(top: Radius.circular(20)),
        border: Border.all(color: goldBorder, width: 1.5),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withOpacity(0.8),
            blurRadius: 20,
            spreadRadius: 5,
          ),
        ],
      ),
      child: SafeArea(
        top: false,
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 12.0),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              // Drag Handle Emas
              Center(
                child: Container(
                  width: 44,
                  height: 4,
                  margin: const EdgeInsets.only(bottom: 16),
                  decoration: BoxDecoration(
                    color: goldBorder.withOpacity(0.6),
                    borderRadius: BorderRadius.circular(2),
                  ),
                ),
              ),

              // Header: Icon di Kiri & Info Item di Kanan
              Row(
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  // Icon Item di Kiri (Membaca dari assets/items/{item_id}.png)
                  Container(
                    width: 76,
                    height: 76,
                    decoration: BoxDecoration(
                      color: obsidianBg,
                      borderRadius: BorderRadius.circular(10),
                      border: Border.all(color: gradeColor, width: 2.2),
                      boxShadow: [
                        BoxShadow(
                          color: gradeColor.withOpacity(0.35),
                          blurRadius: 10,
                          spreadRadius: 1,
                        ),
                      ],
                    ),
                    padding: const EdgeInsets.all(6.0),
                    child: ClipRRect(
                      borderRadius: BorderRadius.circular(6),
                      child: Image.asset(
                        'assets/items/' + item.id + '.png',
                        fit: BoxFit.contain,
                        errorBuilder: (context, error, stackTrace) {
                          return Center(
                            child: Icon(
                              Icons.shield,
                              color: gradeColor,
                              size: 36,
                            ),
                          );
                        },
                      ),
                    ),
                  ),
                  const SizedBox(width: 14),

                  // Detail Nama, Grade, Tipe Senjata, & Level Req
                  Expanded(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          item.name,
                          style: const TextStyle(
                            color: Colors.white,
                            fontSize: 18,
                            fontWeight: FontWeight.bold,
                            letterSpacing: 0.3,
                          ),
                          maxLines: 2,
                          overflow: TextOverflow.ellipsis,
                        ),
                        const SizedBox(height: 6),
                        Row(
                          children: [
                            // Badge Grade Dinamis
                            Container(
                              padding: const EdgeInsets.symmetric(
                                horizontal: 8,
                                vertical: 3,
                              ),
                              decoration: BoxDecoration(
                                color: gradeBgColor,
                                borderRadius: BorderRadius.circular(4),
                                border: Border.all(color: gradeColor, width: 1),
                              ),
                              child: Text(
                                item.grade.toUpperCase(),
                                style: TextStyle(
                                  color: gradeColor,
                                  fontSize: 11,
                                  fontWeight: FontWeight.bold,
                                  letterSpacing: 0.8,
                                ),
                              ),
                            ),
                            const SizedBox(width: 8),

                            // Badge Tipe Senjata
                            Container(
                              padding: const EdgeInsets.symmetric(
                                horizontal: 8,
                                vertical: 3,
                              ),
                              decoration: BoxDecoration(
                                color: const Color(0xFF222736),
                                borderRadius: BorderRadius.circular(4),
                                border: Border.all(
                                  color: const Color(0xFF2E3547),
                                ),
                              ),
                              child: Text(
                                item.weaponType,
                                style: const TextStyle(
                                  color: Color(0xFFB0BEC5),
                                  fontSize: 11,
                                  fontWeight: FontWeight.w600,
                                ),
                              ),
                            ),
                            const Spacer(),

                            // Syarat Level
                            Text(
                              'Lv. ' + item.levelReq.toString(),
                              style: const TextStyle(
                                color: goldAccent,
                                fontSize: 13,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 14),

              // Garis Pemisah Emas
              Container(
                height: 1,
                decoration: BoxDecoration(
                  gradient: LinearGradient(
                    colors: [
                      Colors.transparent,
                      goldBorder.withOpacity(0.5),
                      Colors.transparent,
                    ],
                  ),
                ),
              ),
              const SizedBox(height: 14),

              // Deskripsi Item
              Container(
                width: double.infinity,
                padding: const EdgeInsets.all(10),
                decoration: BoxDecoration(
                  color: obsidianBg,
                  borderRadius: BorderRadius.circular(6),
                  border: Border.all(color: const Color(0xFF222736)),
                ),
                child: Text(
                  item.description,
                  style: const TextStyle(
                    color: Color(0xFFB0BEC5),
                    fontSize: 12,
                    fontStyle: FontStyle.italic,
                  ),
                ),
              ),
              const SizedBox(height: 14),

              // Tab / Section: STATISTIK DASAR
              _buildSectionHeader('STATISTIK DASAR', Icons.flash_on, goldBorder),
              const SizedBox(height: 8),
              Container(
                decoration: BoxDecoration(
                  color: obsidianBg,
                  borderRadius: BorderRadius.circular(8),
                  border: Border.all(color: const Color(0xFF222736)),
                ),
                padding: const EdgeInsets.all(10),
                child: Column(
                  children: [
                    Row(
                      children: [
                        _buildStatCell('Daya Serang (ATK)', '+' + item.attackPower.toString(), goldAccent),
                        _buildStatCell('Kritikal Hit', item.criticalRate.toString() + '%', const Color(0xFFFF8A80)),
                      ],
                    ),
                    const Divider(color: Color(0xFF1E2330), height: 16),
                    Row(
                      children: [
                        _buildStatCell('Kecepatan Serang', item.attackSpeed.toString() + 'x', const Color(0xFF80D8FF)),
                        _buildStatCell('Akurasi (Hit)', '+' + item.accuracy.toString(), const Color(0xFFA7FFEB)),
                      ],
                    ),
                    const Divider(color: Color(0xFF1E2330), height: 16),
                    Row(
                      children: [
                        _buildStatCell('Penetrasi Zirah', '+' + item.penetration.toString(), const Color(0xFFFFD180)),
                        _buildStatCell('Berat Senjata', item.weight.toString(), const Color(0xFFCFD8DC)),
                      ],
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 14),

              // Tab / Section: LOKASI DROP MONSTER (Diparsing dari SQLite l9.db)
              _buildSectionHeader('LOKASI DROP MONSTER (l9.db)', Icons.location_on, goldBorder),
              const SizedBox(height: 8),
              FutureBuilder<List<MonsterDrop>>(
                future: L9DatabaseService.getDropsByItemId(item.id),
                builder: (context, snapshot) {
                  if (snapshot.connectionState == ConnectionState.waiting) {
                    return const Center(
                      child: Padding(
                        padding: EdgeInsets.all(16.0),
                        child: CircularProgressIndicator(color: goldBorder),
                      ),
                    );
                  }
                  final drops = snapshot.data ?? [];
                  if (drops.isEmpty) {
                    return Container(
                      width: double.infinity,
                      padding: const EdgeInsets.all(14),
                      decoration: BoxDecoration(
                        color: obsidianBg,
                        borderRadius: BorderRadius.circular(8),
                      ),
                      child: const Center(
                        child: Text(
                          'Tidak ada data drop monster di database.',
                          style: TextStyle(color: Color(0xFF78909C), fontSize: 12),
                        ),
                      ),
                    );
                  }

                  return Container(
                    decoration: BoxDecoration(
                      color: obsidianBg,
                      borderRadius: BorderRadius.circular(8),
                      border: Border.all(color: const Color(0xFF222736)),
                    ),
                    child: Column(
                      children: drops.map((drop) {
                        return Padding(
                          padding: const EdgeInsets.symmetric(horizontal: 12.0, vertical: 8.0),
                          child: Row(
                            children: [
                              // Icon Tipe Monster
                              Icon(
                                drop.monsterType == 'Magic'
                                    ? Icons.auto_awesome
                                    : (drop.monsterType == 'Ranged' ? Icons.gps_fixed : Icons.shield),
                                size: 18,
                                color: goldAccent,
                              ),
                              const SizedBox(width: 10),
                              // Info Monster & Map
                              Expanded(
                                child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    Text(
                                      drop.monsterName + ' (Lv. ' + drop.monsterLevel.toString() + ')',
                                      style: const TextStyle(
                                        color: Colors.white,
                                        fontSize: 13,
                                        fontWeight: FontWeight.w600,
                                      ),
                                    ),
                                    Text(
                                      drop.mapName,
                                      style: const TextStyle(
                                        color: Color(0xFF78909C),
                                        fontSize: 11,
                                      ),
                                    ),
                                  ],
                                ),
                              ),
                              // Drop Rate Badge
                              Container(
                                padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
                                decoration: BoxDecoration(
                                  color: const Color(0xFF1E3A24),
                                  borderRadius: BorderRadius.circular(4),
                                  border: Border.all(color: const Color(0xFF2E7D32)),
                                ),
                                child: Text(
                                  drop.dropRatePct,
                                  style: const TextStyle(
                                    color: Color(0xFF81C784),
                                    fontSize: 11,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                              ),
                            ],
                          ),
                        );
                      }).toList(),
                    ),
                  );
                },
              ),
              const SizedBox(height: 16),

              // Tombol Tutup Modal
              SizedBox(
                width: double.infinity,
                child: ElevatedButton(
                  onPressed: () => Navigator.pop(context),
                  style: ElevatedButton.styleFrom(
                    backgroundColor: const Color(0xFF222736),
                    foregroundColor: goldBorder,
                    side: const BorderSide(color: goldBorder, width: 1),
                    padding: const EdgeInsets.symmetric(vertical: 12),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(8),
                    ),
                  ),
                  child: const Text(
                    'TUTUP',
                    style: TextStyle(
                      fontWeight: FontWeight.bold,
                      letterSpacing: 1.0,
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  static Widget _buildSectionHeader(String title, IconData icon, Color color) {
    return Row(
      children: [
        Icon(icon, size: 16, color: color),
        const SizedBox(width: 6),
        Text(
          title,
          style: TextStyle(
            color: color,
            fontSize: 12,
            fontWeight: FontWeight.bold,
            letterSpacing: 0.8,
          ),
        ),
      ],
    );
  }

  static Widget _buildStatCell(String label, String value, Color valueColor) {
    return Expanded(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            label,
            style: const TextStyle(color: Color(0xFF90A4AE), fontSize: 11),
          ),
          const SizedBox(height: 2),
          Text(
            value,
            style: TextStyle(
              color: valueColor,
              fontSize: 14,
              fontWeight: FontWeight.bold,
            ),
          ),
        ],
      ),
    );
  }
}
""".trimIndent()
}
