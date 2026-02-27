package com.example.farmacia_inegradora_android.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmacia_inegradora_android.data.PharmacyRepository
import com.example.farmacia_inegradora_android.models.Category
import com.example.farmacia_inegradora_android.models.Product
import com.example.farmacia_inegradora_android.models.Supplier
import com.example.farmacia_inegradora_android.requests.ProductRequest
import com.example.farmacia_inegradora_android.ui_state.ProductUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel: ViewModel() {
    private val  _uiState = MutableStateFlow(ProductUIState())
    val uiState: StateFlow<ProductUIState> = _uiState.asStateFlow()

    private val _categoriesList: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    val categoriesList: StateFlow<List<Category>> = _categoriesList.asStateFlow()
    private val _suppliersList: MutableStateFlow<List<Supplier>> = MutableStateFlow(emptyList())
    val suppliersList: StateFlow<List<Supplier>> = _suppliersList.asStateFlow()

    val repo = PharmacyRepository


    private fun ProductUIState.toProductRequest(): ProductRequest {
        return ProductRequest(
            codigo = this.codigo,
            name = this.name,
            presentation = this.presentation,
            purchase_price = this.purchase_price,
            sale_price = this.sale_price,
            stock = this.stock,
            location = this.location,
            min_stock = this.min_stock,
            max_stock = this.max_stock,
            description = this.description,
            image = this.image,
            category_id = this.category_id,
            supplier_id= this.supplier_id
        )
    }

    fun createProduct(){
        viewModelScope.launch {

            try {
                val request : ProductRequest = _uiState.value.toProductRequest()
                val newProduct: Product = repo.addProducts(request) // a revisar

            }catch (e:Exception){

            }
        }
    }
    fun getCategories(){
        viewModelScope.launch {
            try{
                val categories = repo.getCategories()
                println("CATEGORIAS RECIBIDAS: ${categories.size}") // Mira esto en el Logcat
                _categoriesList.value = categories
            }catch (e: Exception){
                println("ERROR API: ${e.message}") // Mira si sale error aquí
            }
        }
    }
    fun getSuppliers(){
        viewModelScope.launch {
            try{
                val suppliers = repo.getSupplier()
                _suppliersList.value = suppliers
            }catch(e: Exception){

            }
        }
    }
    fun onValueCode(codigo:String){
        _uiState.value = _uiState.value.copy(codigo=codigo)
    }
    fun onValueName(name:String){
        _uiState.value = _uiState.value.copy(name=name)
    }
    fun onValuePresentation(presentation: String){
        _uiState.value = _uiState.value.copy(presentation=presentation)
    }
    fun onValuePurchase(purchase:String){
        var Purchase = purchase.toDoubleOrNull()?:0.0
        _uiState.update { it.copy(purchase_price = Purchase) }
    }
    fun onValueSale(sale:String){
        var Sale = sale.toDoubleOrNull()?:0.0
        _uiState.update { it.copy(sale_price = Sale) }
    }
    fun onValueLocation(location: String){
        _uiState.value = _uiState.value.copy(location=location)
    }

    fun onValueMinStock(minStock:String){
        var MinStock = minStock.toIntOrNull()?:0
        _uiState.update { it.copy(min_stock = MinStock ) }
    }
    fun onValueMaxStock(maxStock:String){
        var MaxStock = maxStock.toIntOrNull()?:0
        _uiState.update { it.copy(max_stock = MaxStock) }
    }
    fun onValueDescription(description: String){
        _uiState.value = _uiState.value.copy(description=description)
    }
    fun onValueCategory(category: Int){

        _uiState.update { it.copy(category_id = category) }
    }
    fun onValueSupplier(supplier: Int){

        _uiState.update { it.copy(supplier_id = supplier) }
    }

}