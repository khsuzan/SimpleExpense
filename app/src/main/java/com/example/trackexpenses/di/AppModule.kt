package com.example.trackexpenses.di

import android.content.Context
import androidx.room.Room
import com.example.trackexpenses.db.ExpenseAppDb
import com.example.trackexpenses.db.ExpenseAppDb.Companion.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomDb(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        ExpenseAppDb::class.java,
        DB_NAME
    ).build()

    @Provides
    fun provideExpenseDao(expenseAppDb: ExpenseAppDb) = expenseAppDb.expenseListDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope