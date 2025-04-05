package com.example.tardinesstracker.data.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(tableName = "tardiness",
        foreignKeys = @ForeignKey(entity = Student.class,
                                parentColumns = "id",
                                childColumns = "studentId",
                                onDelete = ForeignKey.CASCADE))
public class Tardiness {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int studentId;
    private long date;
    private int lateMinutes;
    private String reason;
    private String parentComment;
    private boolean isExcused;
    
    // Constructors
    public Tardiness() {}
    
    public Tardiness(int studentId, long date, int lateMinutes, String reason, 
                    String parentComment, boolean isExcused) {
        this.studentId = studentId;
        this.date = date;
        this.lateMinutes = lateMinutes;
        this.reason = reason;
        this.parentComment = parentComment;
        this.isExcused = isExcused;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getStudentId() {
        return studentId;
    }
    
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    
    public long getDate() {
        return date;
    }
    
    public void setDate(long date) {
        this.date = date;
    }
    
    public int getLateMinutes() {
        return lateMinutes;
    }
    
    public void setLateMinutes(int lateMinutes) {
        this.lateMinutes = lateMinutes;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public String getParentComment() {
        return parentComment;
    }
    
    public void setParentComment(String parentComment) {
        this.parentComment = parentComment;
    }
    
    public boolean isExcused() {
        return isExcused;
    }
    
    public void setExcused(boolean excused) {
        isExcused = excused;
    }
}
