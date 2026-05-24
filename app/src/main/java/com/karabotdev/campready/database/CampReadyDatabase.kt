package com.karabotdev.campready.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.karabotdev.campready.data.CampLocation
import com.karabotdev.campready.data.PackingItem
import com.karabotdev.campready.data.SurvivalTip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [CampLocation::class, PackingItem::class, SurvivalTip::class],
    version = 1,
    exportSchema = false
)
abstract class CampReadyDatabase : RoomDatabase() {

    abstract fun locationDao(): LocationDao
    abstract fun packingDao(): PackingDao
    abstract fun survivalTipDao(): SurvivalTipDao

    companion object {
        @Volatile
        //This is called a Singleton pattern — we only ever want ONE database instance in the whole app.
        // INSTANCE holds that one copy.
        private var INSTANCE: CampReadyDatabase? = null

        fun getDatabase(context: Context): CampReadyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CampReadyDatabase::class.java,
                    "campready_database"
                )
                    .addCallback(PrepopulateCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class PrepopulateCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    prepopulateLocations(database.locationDao())
                    prepopulateTips(database.survivalTipDao())
                    prepopulatePackingItems(database.packingDao())
                }
            }
        }
    }
}

suspend fun prepopulateLocations(dao: LocationDao) {
    val locations = listOf(
        CampLocation(
            name = "Drakensberg Gardens",
            region = "KwaZulu-Natal",
            description = "Stunning mountain scenery with hiking trails and waterfalls. One of SA's most iconic camping destinations.",
            difficulty = "Moderate",
            firesAllowed = true,
            hasSignal = false,
            distanceToTown = "45km to Underberg",
            dangers = "Sudden weather changes, steep trails, occasional baboons"
        ),
        CampLocation(
            name = "Boulders Beach Camp",
            region = "Western Cape",
            description = "Coastal camping near the famous penguin colony. Perfect for family trips.",
            difficulty = "Easy",
            firesAllowed = false,
            hasSignal = true,
            distanceToTown = "2km to Simon's Town",
            dangers = "Strong winds, sunburn, protect food from seagulls"
        ),
        CampLocation(
            name = "Kruger National Park",
            region = "Limpopo/Mpumalanga",
            description = "World famous Big Five safari camping. An unforgettable African wilderness experience.",
            difficulty = "Easy",
            firesAllowed = true,
            hasSignal = true,
            distanceToTown = "Various gates with nearby towns",
            dangers = "Wildlife — never walk outside camp at night, elephant and lion active"
        ),
        CampLocation(
            name = "Cederberg Wilderness",
            region = "Western Cape",
            description = "Remote mountain camping with ancient San rock art and dramatic rock formations.",
            difficulty = "Hard",
            firesAllowed = false,
            hasSignal = false,
            distanceToTown = "60km to Clanwilliam",
            dangers = "Extreme heat in summer, limited water sources, no rescue services"
        ),
        CampLocation(
            name = "Magoebaskloof",
            region = "Limpopo",
            description = "Lush indigenous forest camping near waterfalls and tea plantations. Close to Pretoria.",
            difficulty = "Easy",
            firesAllowed = true,
            hasSignal = true,
            distanceToTown = "15km to Tzaneen",
            dangers = "Slippery trails when wet, occasional leopard sightings"
        )
    )
    locations.forEach { dao.upsert(it) }
}

suspend fun prepopulateTips(dao: SurvivalTipDao) {
    val tips = listOf(
        SurvivalTip(
            title = "Water Purification",
            content = "Always purify water from streams. Use purification tablets or boil for at least 1 minute. Never drink directly from any water source in the wild.",
            category = "Water"
        ),
        SurvivalTip(
            title = "Fire Safety",
            content = "Always check if fires are allowed. Clear a 1m radius around your fire. Never leave a fire unattended. Drown it completely before sleeping.",
            category = "Fire"
        ),
        SurvivalTip(
            title = "Wildlife Encounters",
            content = "Never run from predators — back away slowly. Make noise while hiking to avoid surprising animals. Store food in sealed containers away from your tent.",
            category = "Wildlife"
        ),
        SurvivalTip(
            title = "Navigation",
            content = "Download offline maps before leaving signal range. Always tell someone your route and expected return. Carry a physical compass as backup.",
            category = "Navigation"
        ),
        SurvivalTip(
            title = "Weather Awareness",
            content = "In the Drakensberg, storms can arrive in minutes. Watch for dark clouds building. Descend from high ground immediately if lightning threatens.",
            category = "Weather"
        ),
        SurvivalTip(
            title = "Snake Safety",
            content = "Always check your boots before putting them on. Never put hands in crevices you can't see. If bitten, keep calm and get to hospital immediately.",
            category = "Wildlife"
        )
    )
    tips.forEach { dao.upsert(it) }
}

suspend fun prepopulatePackingItems(dao: PackingDao) {
    val items = listOf(
        PackingItem(name = "Tent", category = "Shelter"),
        PackingItem(name = "Sleeping Bag", category = "Shelter"),
        PackingItem(name = "Sleeping Mat", category = "Shelter"),
        PackingItem(name = "Headlamp + Batteries", category = "Lighting"),
        PackingItem(name = "First Aid Kit", category = "Safety"),
        PackingItem(name = "Water Purification Tablets", category = "Water"),
        PackingItem(name = "Water Bottles (2L)", category = "Water"),
        PackingItem(name = "Fire Starter", category = "Fire"),
        PackingItem(name = "Matches (waterproof)", category = "Fire"),
        PackingItem(name = "Pocket Knife", category = "Tools"),
        PackingItem(name = "Compass", category = "Navigation"),
        PackingItem(name = "Offline Maps Downloaded", category = "Navigation"),
        PackingItem(name = "Sunscreen SPF50", category = "Health"),
        PackingItem(name = "Insect Repellent", category = "Health"),
        PackingItem(name = "Rain Jacket", category = "Clothing"),
        PackingItem(name = "Warm Fleece", category = "Clothing"),
        PackingItem(name = "Hiking Boots", category = "Clothing"),
        PackingItem(name = "Cooking Pot", category = "Food"),
        PackingItem(name = "Camp Stove + Gas", category = "Food"),
        PackingItem(name = "High Energy Snacks", category = "Food")
    )
    items.forEach { dao.upsert(it) }
}