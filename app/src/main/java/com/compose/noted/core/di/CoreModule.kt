package com.compose.noted.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.compose.noted.core.data.NoteRepository
import com.compose.noted.core.data.local.NoteDatabase
import com.compose.noted.domain.usecase.AddNote
import com.compose.noted.domain.usecase.DeleteNote
import com.compose.noted.domain.usecase.GetNote
import com.compose.noted.domain.usecase.GetNotes
import com.compose.noted.domain.usecase.NoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepository(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideUseCase(repository: NoteRepository): NoteUseCase {
        return NoteUseCase(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }


}