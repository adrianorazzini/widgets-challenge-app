package br.com.adrianorazzini.widgetchallenge.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.adrianorazzini.remote.controller.CardController
import br.com.adrianorazzini.remote.controller.StatementController
import br.com.adrianorazzini.remote.controller.WidgetController
import br.com.adrianorazzini.remote.model.*
import br.com.adrianorazzini.widgetchallenge.common.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MainViewModelTest {

    // Mock Widget Data
    private val mockActionContent = ActionContent("123", "123")
    private val mockAction = ButtonAction("123", mockActionContent)
    private val mockWidgetButton = WidgetButton("Mock Title", mockAction)
    private val mockBalance = Balance("Mock Label", "Mock Value")
    private val mockWidgetContent = WidgetContent("Mock Title", "1234 5678 9012 3456", mockBalance, mockWidgetButton)
    private val mockWidget = WidgetData(Identifier.HOME_HEADER_WIDGET.value, mockWidgetContent)

    // Mock Card Data
    private val mockCard =
        Card("1234 5678 9012 3456", "Mock Name", "12/20", "R$ 1.000,00", "R$ 2.000,00")

    // Mock Statement Data
    private val mockTransaction = Transaction("Mock Label", "R$ 100,00", "Mock Description")
    private val mockStatement = Statement(mockBalance, arrayListOf(mockTransaction))

    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var widgetController: WidgetController
    @Mock
    private lateinit var cardController: CardController
    @Mock
    private lateinit var statementController: StatementController

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel()
    }

    @Test
    fun `#clear should restore all public variables to initial value`() {
        viewModel.homeHeaderField.set("Olá Fulano")
        viewModel.cardInfoField.set(Card("", "", "", "", ""))
        viewModel.balanceInfoField.set(Balance("", ""))
        viewModel.clear()

        assertThat(viewModel.homeHeaderField.get()).isEqualTo("")
        assertThat(viewModel.cardInfoField.get()).isNull()
        assertThat(viewModel.balanceInfoField.get()).isNull()
    }

    @Test
    fun `#loadWidgets should not add items to homeCardItems if request failed`() = mainCoroutineRule
        .runBlockingTest {
            Mockito.`when`(widgetController.getWidgets()).thenReturn(null)
            viewModel.loadWidgets()

            assertThat(viewModel.homeCardItems.size).isEqualTo(0)
        }

    @Test
    fun `#loadWidgets should add items to homeCardItems if request succeed`() = mainCoroutineRule
        .runBlockingTest {
            val mockWidgets = ArrayList<WidgetData>()
            mockWidgets.add(mockWidget)
            Mockito.`when`(widgetController.getWidgets()).thenReturn(mockWidgets)
            viewModel.loadWidgets()

            // TODO Assert
        }

    @Test
    fun `#loadWidgets should set header content if request succeed`() = mainCoroutineRule
        .runBlockingTest {
            val mockWidgets = ArrayList<WidgetData>()
            mockWidgets.add(mockWidget)
            Mockito.`when`(widgetController.getWidgets()).thenReturn(mockWidgets)
            viewModel.loadWidgets()

            // TODO Assert
        }

    @Test
    fun `#setSelectedCardId should store the card id according to the position selected`() {
        // TODO Test
    }

    @Test
    fun `#loadCardInfo should update card info if request succeed`() = mainCoroutineRule
        .runBlockingTest {
            Mockito.`when`(cardController.getCardInfo("123")).thenReturn(mockCard)
            viewModel.loadCardInfo()

            // TODO Assert
        }

    @Test
    fun `#loadCardInfo should handle error if card Id is not '123'`() = mainCoroutineRule
        .runBlockingTest {
            Mockito.`when`(cardController.getCardInfo("000")).thenReturn(null)
            viewModel.loadCardInfo()

            // TODO Assert
        }

    @Test
    fun `#clearCardInfo should restore the public variables related to Card data`() {
        viewModel.selectedCardId = "123"
        viewModel.clearCardInfo()

        assertThat(viewModel.selectedCardId).isNull()
    }

    @Test
    fun `#setSelectedAccountId should store the Account id according to the position selected`() {
        // TODO Test
    }

    @Test
    fun `#loadStatementInfo should update statement info if request succeed`() = mainCoroutineRule
        .runBlockingTest {
            Mockito.`when`(statementController.getStatement("123")).thenReturn(mockStatement)
            viewModel.loadStatementInfo()

            // TODO Assert
        }

    @Test
    fun `#loadStatementInfo should handle error if account Id is not '123'`() = mainCoroutineRule
        .runBlockingTest {
            Mockito.`when`(statementController.getStatement("000")).thenReturn(null)
            viewModel.loadStatementInfo()

            // TODO Assert
        }

    @Test
    fun `#clearStatementInfo should restore the public variables related to Account|Statement data`() {
        viewModel.selectedAccountId = "123"
        viewModel.clearStatementInfo()

        assertThat(viewModel.selectedAccountId).isNull()
    }
}