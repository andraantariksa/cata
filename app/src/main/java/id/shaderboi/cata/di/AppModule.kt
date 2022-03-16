package id.shaderboi.cata.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.shaderboi.cata.feature_todo.data.data_source.ToDosDatabase
import id.shaderboi.cata.feature_todo.data.repository.NoteRepositoryImpl
import id.shaderboi.cata.feature_todo.domain.repository.NoteRepository
import id.shaderboi.cata.feature_todo.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): ToDosDatabase {
        return Room.databaseBuilder(app, ToDosDatabase::class.java, ToDosDatabase.NAME).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: ToDosDatabase): NoteRepository {
        return NoteRepositoryImpl(db.toDoDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): ToDoUseCases {
        return ToDoUseCases(
            GetToDosUseCase(repository),
            GetToDoUseCase(repository),
            UpdateToDoUseCase(repository),
            DeleteToDoUseCase(repository),
            InsertToDoUseCase(repository)
        )
    }
}