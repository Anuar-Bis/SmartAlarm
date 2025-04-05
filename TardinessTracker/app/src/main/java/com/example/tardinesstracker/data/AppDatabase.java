package com.example.tardinesstracker.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.tardinesstracker.data.dao.ParentDao;
import com.example.tardinesstracker.data.dao.StudentDao;
import com.example.tardinesstracker.data.dao.TeacherDao;
import com.example.tardinesstracker.data.dao.TardinessDao;
import com.example.tardinesstracker.data.entities.Parent;
import com.example.tardinesstracker.data.entities.Student;
import com.example.tardinesstracker.data.entities.Teacher;
import com.example.tardinesstracker.data.entities.Tardiness;
import com.example.tardinesstracker.utils.DateConverter;

@Database(entities = {Student.class, Parent.class, Teacher.class, Tardiness.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    
    private static final String DATABASE_NAME = "tardiness_tracker_db";
    private static AppDatabase instance;
    
    // DAOs
    public abstract StudentDao studentDao();
    public abstract ParentDao parentDao();
    public abstract TeacherDao teacherDao();
    public abstract TardinessDao tardinessDao();
    
    // Singleton pattern
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
