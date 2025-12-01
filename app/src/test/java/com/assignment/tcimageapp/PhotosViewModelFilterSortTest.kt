package com.assignment.tcimageapp

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.assignment.tcimageapp.mock.MainDispatcherRule
import com.assignment.tcimageapp.core.PhotosSortOption
import com.assignment.tcimageapp.data.local.OfflineSettingsDataSource
import com.assignment.tcimageapp.data.remote.dto.PhotoDto
import com.assignment.tcimageapp.domain.action.ClearPhotosCacheAction
import com.assignment.tcimageapp.domain.action.GetOfflineEnabledAction
import com.assignment.tcimageapp.domain.action.GetPhotosAction
import com.assignment.tcimageapp.domain.action.GetSelectedAuthorAction
import com.assignment.tcimageapp.domain.action.SaveOfflineEnabledAction
import com.assignment.tcimageapp.domain.action.SaveSelectedAuthorAction
import com.assignment.tcimageapp.domain.repository.PhotosRepository
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import com.assignment.tcimageapp.core.internet.NetworkState
import com.assignment.tcimageapp.domain.repository.AuthorRepository
import com.assignment.tcimageapp.mock.FakeAuthorRepository
import com.assignment.tcimageapp.mock.FakePhotosRepository
import com.assignment.tcimageapp.mock.InMemoryPreferencesDataStore
import com.assignment.tcimageapp.mock.NetworkMonitorFake
import com.assignment.tcimageapp.presentation.feature.PhotosViewModel

@OptIn(ExperimentalCoroutinesApi::class)
class PhotosViewModelFilterSortTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private lateinit var fakeRepository: PhotosRepository
    private lateinit var fakeAuthorRepository: AuthorRepository
    private lateinit var offlineSettingsDataSource: OfflineSettingsDataSource
    private lateinit var getSelectedAuthorAction: GetSelectedAuthorAction
    private lateinit var saveSelectedAuthorAction: SaveSelectedAuthorAction
    private lateinit var clearPhotosCacheAction: ClearPhotosCacheAction
    private lateinit var getOfflineEnabledAction: GetOfflineEnabledAction
    private lateinit var saveOfflineEnabledAction: SaveOfflineEnabledAction
    private lateinit var getPhotosAction: GetPhotosAction
    private lateinit var fakeLocalRepository: PhotosRepository
    private lateinit var networkMonitor: NetworkState
    private lateinit var photos: List<PhotoDto>
    private lateinit var inMemoryDataStore: InMemoryPreferencesDataStore

    @Before
    fun setup() {
        photos = listOf(
            photo(id = "1", author = "Chinmay", height = 500),
            photo(id = "2", author = "Anil",   height = 800),
            photo(id = "3", author = "Bro",     height = 600),
            photo(id = "4", author = "Anil",   height = 900)
        )

        fakeRepository = FakePhotosRepository(photos)
        fakeLocalRepository = FakePhotosRepository(photos)
        fakeAuthorRepository = FakeAuthorRepository()
        networkMonitor = NetworkMonitorFake(true)
        inMemoryDataStore = InMemoryPreferencesDataStore()
        offlineSettingsDataSource = OfflineSettingsDataSource(inMemoryDataStore)
        getSelectedAuthorAction = GetSelectedAuthorAction(fakeAuthorRepository)
        saveSelectedAuthorAction = SaveSelectedAuthorAction(fakeAuthorRepository)
        clearPhotosCacheAction = ClearPhotosCacheAction(fakeRepository)
        getOfflineEnabledAction = GetOfflineEnabledAction(offlineSettingsDataSource)
        saveOfflineEnabledAction = SaveOfflineEnabledAction(offlineSettingsDataSource)
        getPhotosAction = GetPhotosAction(networkMonitor,fakeRepository,fakeLocalRepository)
    }

    private fun createViewModel(): PhotosViewModel {
        return PhotosViewModel(
            getSelectedAuthorAction = getSelectedAuthorAction,
            saveSelectedAuthorAction = saveSelectedAuthorAction,
            clearPhotosCacheAction = clearPhotosCacheAction,
            getOfflineEnabledAction = getOfflineEnabledAction,
            saveOfflineEnabledAction = saveOfflineEnabledAction,
            getPhotosAction = getPhotosAction
        )
    }

    private fun photo(
        id: String,
        author: String,
        width: Int = 1000,
        height: Int
    ): PhotoDto = PhotoDto(
        id = id,
        author = author,
        width = width,
        height = height,
        url = "https://picsum.com/$id",
        downloadUrl = "https://picsum.photos/id/$id/$width/$height"
    )

    @Test
    fun when_initialized_with_no_filter_and_DEFAULT_sort_all_photos_are_shown() = runTest {
        // Arrange
        val viewModel = createViewModel()
        runCurrent()

        val state = viewModel.myPhotos.value

        // Assert
        assertEquals(photos, state.allPhotos)
        assertEquals(photos, state.filteredPhotos)
        assertEquals(null, state.selectedAuthor)
        assertEquals(PhotosSortOption.DEFAULT, state.sortOption)
        assertTrue(!state.isLoading)
        assertEquals(null, state.errorMessage)
    }

    @Test
    fun filter_by_author_shows_only_that_authors_photos() = runTest {
        val viewModel = createViewModel()
        runCurrent()

        // Act: select "Anil"
        viewModel.onAuthorSelected("Anil")
        runCurrent()

        val state = viewModel.myPhotos.value

        // Assert
        assertEquals("Anil", state.selectedAuthor)
        assertTrue(state.filteredPhotos.all { it.author == "Anil" })
        assertEquals(listOf("2", "4"), state.filteredPhotos.map { it.id })
    }

    @Test
    fun sort_by_AUTHOR_sorts_photos_by_author_name_descending() = runTest {
        val viewModel = createViewModel()
        runCurrent()

        // Act
        viewModel.onSortOptionSelected(PhotosSortOption.AUTHOR)
        runCurrent()

        val state = viewModel.myPhotos.value

        assertEquals(PhotosSortOption.AUTHOR, state.sortOption)

        val authorsOrder = state.filteredPhotos.map { it.author }
        // Expected descending by author Chinmay, Bro, Anil, Anil
        assertEquals(listOf("Chinmay", "Bro", "Anil", "Anil"), authorsOrder)
    }

    @Test
    fun sort_by_HEIGHT_sorts_photos_by_height_descending() = runTest {
        val viewModel = createViewModel()
        runCurrent()

        // Act
        viewModel.onSortOptionSelected(PhotosSortOption.HEIGHT)
        runCurrent()

        val state = viewModel.myPhotos.value

        assertEquals(PhotosSortOption.HEIGHT, state.sortOption)

        val heightsOrder = state.filteredPhotos.map { it.height }
        assertEquals(listOf(900, 800, 600, 500), heightsOrder)
    }

    @Test
    fun filter_by_author_then_sort_by_HEIGHT_applies_both() = runTest {
        val viewModel = createViewModel()
        runCurrent()

        // Act filter first
        viewModel.onAuthorSelected("Anil")
        runCurrent()

        // Then sort by HEIGHT
        viewModel.onSortOptionSelected(PhotosSortOption.HEIGHT)
        runCurrent()

        val state = viewModel.myPhotos.value

        assertEquals("Anil", state.selectedAuthor)
        assertEquals(PhotosSortOption.HEIGHT, state.sortOption)

        // Only Anil photos sorted by height desc -> 900, 800
        assertTrue(state.filteredPhotos.all { it.author == "Anil" })
        assertEquals(listOf(900, 800), state.filteredPhotos.map { it.height })
        assertEquals(listOf("4", "2"), state.filteredPhotos.map { it.id })
    }

    @Test
    fun changing_sort_does_not_clear_selected_author() = runTest {
        val viewModel = createViewModel()
        runCurrent()

        // Select Bro
        viewModel.onAuthorSelected("Bro")
        runCurrent()

        // Now change sort
        viewModel.onSortOptionSelected(PhotosSortOption.AUTHOR)
        runCurrent()

        val state = viewModel.myPhotos.value

        assertEquals("Bro", state.selectedAuthor)
        assertTrue(state.filteredPhotos.all { it.author == "Bro" })
        assertEquals(PhotosSortOption.AUTHOR, state.sortOption)
    }
}


