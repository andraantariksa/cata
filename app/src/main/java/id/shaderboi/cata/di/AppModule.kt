package id.shaderboi.cata.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.shaderboi.cata.feature_todo.data.data_source.ToDosDatabase
import id.shaderboi.cata.feature_todo.data.repository.ToDoRepositoryImpl
import id.shaderboi.cata.feature_todo.domain.repository.ToDoRepository
import id.shaderboi.cata.feature_todo.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): ToDosDatabase {
        return Room.databaseBuilder(context, ToDosDatabase::class.java, ToDosDatabase.NAME).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: ToDosDatabase): ToDoRepository {
        return ToDoRepositoryImpl(db.toDoDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: ToDoRepository): ToDoUseCases {
        return ToDoUseCases(
            GetToDosUseCase(repository),
            GetToDoUseCase(repository),
            UpdateToDoUseCase(repository),
            DeleteToDoUseCase(repository),
            InsertToDoUseCase(repository)
        )
    }
}