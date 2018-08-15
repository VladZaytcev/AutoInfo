package com.baikaleg.v3.autoinfo.data

import android.content.Context
import com.baikaleg.v3.autoinfo.data.db.AppDB
import com.baikaleg.v3.autoinfo.data.db.Station
import io.reactivex.disposables.CompositeDisposable

class Repository private constructor(context: Context) {
    private val db: AppDB? = AppDB.getInstance(context)
    private val cd: CompositeDisposable = CompositeDisposable()

    companion object {

        private var INSTANCE: Repository? = null

        fun getInstance(context: Context): Repository {
            if (INSTANCE == null) {
                INSTANCE = Repository(context)
            }
            return INSTANCE as Repository
        }
    }

    fun getStations(route: String): List<Station>? {
       return db?.stationDao()?.getAllStationsInRoute(route)?.map { t: List<Station> -> t}?.blockingFirst()?.toList()
    }

    fun saveStation(station: Station){
        db?.stationDao()?.insert(station)
    }

    fun close(){
        cd.clear()
    }
}
