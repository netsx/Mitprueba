import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {

    private val LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[LOGGED_IN_KEY] ?: false
    }

    suspend fun setLoggedIn(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[LOGGED_IN_KEY] = value
        }
    }
}
