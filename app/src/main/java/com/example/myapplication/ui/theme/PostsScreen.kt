package com.liceo.mysocial.ui

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.liceo.mysocial.data.Post
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsScreen(vm: PostsViewModel) {
    val posts by vm.posts.collectAsStateWithLifecycle()
    var editing by remember { mutableStateOf<Post?>(null) }
    var showEditor by remember { mutableStateOf(false) }
    var postToDelete by remember { mutableStateOf<Post?>(null) }
    
    var searchQuery by remember { mutableStateOf("") }
    val filteredPosts = remember(searchQuery, posts) {
        if (searchQuery.isEmpty()) posts
        else posts.filter { it.content.contains(searchQuery, ignoreCase = true) }
    }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text("Pahayahay Social", fontWeight = FontWeight.Black)
                        Text(
                            "${posts.size} posts published", 
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("New Post") },
                icon = { Icon(Icons.Default.Add, null) },
                onClick = { editing = null; showEditor = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Cool Feature: Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search posts...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                leadingIcon = { Icon(Icons.Default.Search, null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Close, null)
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )

            if (filteredPosts.isEmpty()) {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text(
                        if (searchQuery.isEmpty()) "Your feed is empty" else "No posts match \"$searchQuery\"",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredPosts, key = { it.id }) { post ->
                        PostCard(
                            post = post,
                            onEdit = { editing = post; showEditor = true },
                            onDelete = { postToDelete = post },
                        )
                    }
                }
            }
        }
    }

    if (showEditor) {
        PostEditorDialog(
            initialText = editing?.content ?: "",
            onDismiss = { showEditor = false },
            onSave = { newText ->
                val current = editing
                if (current == null) vm.addPost(newText) else vm.editPost(current, newText)
                showEditor = false
            },
        )
    }

    postToDelete?.let { post ->
        AlertDialog(
            onDismissRequest = { postToDelete = null },
            title = { Text("Delete post?", fontWeight = FontWeight.Bold) },
            text = { Text("This will permanently remove your post.") },
            confirmButton = {
                Button(
                    onClick = {
                        vm.deletePost(post)
                        postToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { postToDelete = null }) { Text("Cancel") }
            }
        )
    }
}

@Composable
fun PostCard(post: Post, onEdit: () -> Unit, onDelete: () -> Unit) {
    val time = remember(post.createdAt) {
        SimpleDateFormat("h:mm a · MMM d, yyyy", Locale.getDefault()).format(Date(post.createdAt))
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // User Avatar Placeholder
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("T", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                }
                
                Spacer(Modifier.width(12.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Tristan Pahayahay",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(time, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
                
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.MoreVert, null, tint = Color.Gray)
                }
            }
            
            Spacer(Modifier.height(12.dp))
            
            Text(
                post.content,
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 24.sp
            )
            
            Spacer(Modifier.height(16.dp))
            
            HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    TextButton(onClick = { /* Simulated Like */ }) {
                        Icon(Icons.Outlined.FavoriteBorder, null, Modifier.size(18.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Like", style = MaterialTheme.typography.labelLarge)
                    }
                    TextButton(onClick = { /* Simulated Share */ }) {
                        Icon(Icons.Outlined.Share, null, Modifier.size(18.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Share", style = MaterialTheme.typography.labelLarge)
                    }
                }
                
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, null, tint = Color.Red.copy(alpha = 0.6f), modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}

@Composable
fun PostEditorDialog(
    initialText: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
) {
    var text by rememberSaveable { mutableStateOf(initialText) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initialText.isEmpty()) "Create Post" else "Edit Post", fontWeight = FontWeight.Bold) },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                placeholder = { Text("What's happening?") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
                shape = RoundedCornerShape(12.dp)
            )
        },
        confirmButton = {
            Button(
                onClick = { onSave(text.trim()) },
                enabled = text.isNotBlank(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Post")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
    )
}
