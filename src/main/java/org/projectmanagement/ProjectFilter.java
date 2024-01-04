package org.projectmanagement;


// TODO dokończ implementację interfejsu przez dodanie funkcji, która zwraca
// true jeśli projekt spełnia dany warunek, i false, gdy nie spełnia.
// Następnie wykorzystaj w metodzie ProjectManager.java.filterProjects(...).
// Sygnatura funkcji i sam interfejs wymagają wykorzystania gneryków, które tu usunąłem.
// Zastanów się, czy w intefejsie potrzebne jest ciało funkcji?

public interface ProjectFilter<T> {
    boolean filter(Project project, T criteria);
}


