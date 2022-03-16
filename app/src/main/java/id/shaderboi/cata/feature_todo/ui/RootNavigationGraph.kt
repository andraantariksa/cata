package id.shaderboi.cata.feature_todo.ui

sealed class RootNavigationGraph(val route: String) {
    object HomeRootNavigationGraph: RootNavigationGraph("home")
    object ToDoRootNavigationGraph: RootNavigationGraph("todo")
}