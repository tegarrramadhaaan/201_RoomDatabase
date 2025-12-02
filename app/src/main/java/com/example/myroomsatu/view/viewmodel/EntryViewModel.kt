package com.example.myroomsatu.view.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myroomsatu.repositori.RepositoriSiswa
import com.example.myroomsatu.room.Siswa

class EntryViewModel(private val repositoriSiswa: RepositoriSiswa) : ViewModel(){

    /**
     * Berisi status siswa saat ini
     */
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    /**
     * Fungsi untuk memvalidasi input
     */
    private fun validasiInput(detailSiswa: DetailSiswa = uiStateSiswa.detailSiswa): Boolean{
        return with(detailSiswa){
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    fun updateUiState(detailSiswa: DetailSiswa){
        uiStateSiswa = UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput(detailSiswa))
    }

    /**
     * Fungsi untuk menyimpan data yang dientry
     */
    suspend fun saveSiswa(){
        if (validasiInput()){
            repositoriSiswa.insertSiswa(uiStateSiswa.detailSiswa.toSiswa())
        }
    }
}

/**
 * Mewakili Status UI untuk Siswa
 */
data class UIStateSiswa(
    val detailSiswa: DetailSiswa = DetailSiswa(),
    val isEntryValid: Boolean = false
)

data class DetailSiswa(
    val id: Int = 0,
    val nama: String = "",
    val alamat: String = "",
    val telpon: String = ""
)

/**
 * Fungsi ekstensi untuk mengonversi data kelas [DetailSiswa] menjadi data kelas [Siswa]
 * yang dapat disimpan di database
 */
fun DetailSiswa.toSiswa(): Siswa = Siswa(
    id = id,
    nama = nama,
    alamat = alamat,
    telpon = telpon
)


