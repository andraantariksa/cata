package id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.components.sort_todo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsWithImePadding
import id.shaderboi.cata.feature_todo.domain.model.sorting.Order
import id.shaderboi.cata.feature_todo.domain.model.sorting.ToDoOrderField
import id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.view_model.ToDosEvent
import id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.view_model.ToDosViewModel

@Composable
fun SortToDoModal(
    toDosViewModel: ToDosViewModel,
) {
    AnimatedVisibility(
        visible = toDosViewModel.isSortToDoModalOpened,
        enter = fadeIn(animationSpec = tween()),
        exit = fadeOut(animationSpec = tween()),
    ) {
        Box {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0.0f, 0.0f, 0.0f, 0.7f))
                    .clickable {
                        toDosViewModel.onEvent(ToDosEvent.ToggleSortToDoModal)
                    }
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
            ) {
                Surface(
                    modifier = Modifier.navigationBarsWithImePadding()
                ) {
                    Column(
                        modifier = Modifier.padding(
                            start = 20.dp,
                            end = 20.dp,
                            bottom = 30.dp,
                            top = 15.dp
                        ),
                    ) {
                        Text(
                            "Sort",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Column {
                                ToDoOrderField::class.nestedClasses.forEach { orderField ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        val toDoOrder = toDosViewModel.toDosState.toDoOrder
                                        RadioButton(
                                            selected = orderField.objectInstance == toDoOrder.toDoOrderField,
                                            onClick = {
                                                toDosViewModel.onEvent(
                                                    ToDosEvent.Order(toDoOrder.copy(toDoOrderField = orderField.objectInstance as ToDoOrderField))
                                                )
                                            }
                                        )
                                        Text(orderField.simpleName!!)
                                    }
                                }
                            }
                            Column {
                                Order::class.nestedClasses.forEach { order ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        val toDoOrder = toDosViewModel.toDosState.toDoOrder
                                        RadioButton(
                                            selected = order.objectInstance == toDoOrder.order,
                                            onClick = {
                                                toDosViewModel.onEvent(
                                                    ToDosEvent.Order(toDoOrder.copy(order = order.objectInstance as Order))
                                                )
                                            }
                                        )
                                        Text(order.simpleName!!)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
