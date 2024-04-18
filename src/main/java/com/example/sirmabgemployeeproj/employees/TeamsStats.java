package com.example.sirmabgemployeeproj.employees;

import org.apache.catalina.LifecycleState;

import java.util.List;

public record TeamsStats(Integer projectId,
                        List< Integer > emplIDs,
                         Long timeWorkedOnTheProj) {
}
