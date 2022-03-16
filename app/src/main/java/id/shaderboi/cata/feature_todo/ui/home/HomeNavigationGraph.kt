package id.shaderboi.cata.feature_todo.ui.home

sealed class HomeNavigationGraph(val route: String) {
    object ToDos : HomeNavigationGraph("todos")
    object Misc : HomeNavigationGraph("misc")
}

val homeNavigations = listOf(
    HomeNavigationGraph.ToDos,
    HomeNavigationGraph.Misc
)
