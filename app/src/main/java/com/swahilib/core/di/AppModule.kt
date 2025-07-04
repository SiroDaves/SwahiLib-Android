package com.swahilib.core.di

import android.content.Context
import android.content.SharedPreferences
import com.swahilib.core.utils.Preferences
import com.swahilib.domain.repository.*
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [DatabaseModule::class, SupabaseModule::class])
class AppModule {
    @Provides
    @Singleton
    fun provideSharePreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Preferences.PREFERENCE_FILE, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideHistoryRepository(
        @ApplicationContext context: Context,
        supabase: Postgrest,
    ): HistoryRepository = HistoryRepository(context, supabase)

    @Provides
    @Singleton
    fun provideIdiomRepository(
        @ApplicationContext context: Context,
        supabase: Postgrest,
    ): IdiomRepository = IdiomRepository(context, supabase)

    @Provides
    @Singleton
    fun provideProverbRepository(
        @ApplicationContext context: Context,
        supabase: Postgrest,
    ): ProverbRepository = ProverbRepository(context, supabase)

    @Provides
    @Singleton
    fun provideSayingRepository(
        @ApplicationContext context: Context,
        supabase: Postgrest,
    ): SayingRepository = SayingRepository(context, supabase)

    @Provides
    @Singleton
    fun provideSearchRepository(
        @ApplicationContext context: Context,
        supabase: Postgrest,
    ): SearchRepository = SearchRepository(context, supabase)

    @Provides
    @Singleton
    fun provideWordRepository(
        @ApplicationContext context: Context,
        supabase: Postgrest,
    ): WordRepository = WordRepository(context, supabase)

}