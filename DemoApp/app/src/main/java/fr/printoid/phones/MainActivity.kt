package fr.printoid.phones

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import fr.printoid.phones.adapter.ServerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.content.pm.PackageManager
import android.text.TextUtils
import fr.printoid.phones.adapter.listener.OnServerClickedListener

/**
 * A very simple example to handle the communication with Printoid using
 * the provided AIDL.
 *
 * In this example:
 * - a simple button to request the list of configured profiles in Printoid
 * - a recycler view to display this list
 * - items in the list can be clicked to send a dummy URL to Printoid for the selected server
 *
 * Please follow the best practices defined by Android here:
 * https://developer.android.com/guide/components/aidl
 */
class MainActivity : AppCompatActivity() {

    ///////////////
    // Constants //
    ///////////////

    companion object {
        private const val TAG = "AIDL-DemoApp"

        private const val DUMMY_URL = "https://my-server.com/the-best-file.gcode"

        /** Remote action key */
        private const val REMOTE_ACTION = "fr.printoid.phones.PrintoidCommunicationService.BIND"
        /** Targeted service classname */
        private const val SERVICE_CLASSNAME = "fr.yochi376.octodroid.ipc.PrintoidCommunicationService"

        /** Printoid PREMIUM package name */
        private const val PRINTOID_PRO_PN = "fr.yochi76.printoid.phones.pro"
        /** Printoid PRO package name */
        private const val PRINTOID_PREMIUM_PN = "fr.yochi76.printoid.phones.premium"
        /** Printoid LITE package name */
        private const val PRINTOID_LITE_PN = "fr.yochi76.printoid.phones.trial"
    }

    ////////////////
    // Attributes //
    ////////////////

    private lateinit var rcvAdapter: ServerAdapter

    private var service: IPrintoidCommunicationInterface? = null
    private var isBound = false

    ///////////////
    // LifeCycle //
    ///////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeRecycler()
        initializeListeners()

        bindToService()
    }

    override fun onDestroy() {
        if (isBound) {
            unbindService(connection)
        }
        super.onDestroy()
    }

    //////////////////////
    // Business methods //
    //////////////////////

    private fun initializeRecycler() {
        val rcvLayoutManager = LinearLayoutManager(this)
        rcvAdapter = ServerAdapter(object : OnServerClickedListener {
            override fun onServerClicked(server: ServerModel) = sendDummyUrlTo(server)
        })

        rcv_profiles.apply {
            adapter = rcvAdapter
            layoutManager = rcvLayoutManager
            setHasFixedSize(true)
        }
    }

    private fun initializeListeners() {
        btn_request_profiles.setOnClickListener { loadProfiles() }
    }

    /**
     * Load list of configured profiles from Printoid
     */
    private fun loadProfiles() {
        try {
            val servers = service?.configuredServers

            if (servers != null) {
                rcvAdapter.apply {
                    items = servers
                    notifyDataSetChanged()
                }
            }
        } catch (e: RemoteException) {
            Log.e(TAG, "loadProfiles.Remote exception: ", e)
        }
    }

    /**
     * Send dummy URL for the passed server to Printoid
     */
    private fun sendDummyUrlTo(server: ServerModel) {
        Log.i(TAG, "doOnServerClicked: $server")

        try {
            service?.sendGcodeFromUrlToServer(DUMMY_URL, server.serverId)

        } catch (e: RemoteException) {
            Log.e(TAG, "sendDummyUrlTo.Remote exception: ", e)
        }
    }

    ///////////////////////////////
    // Binding with AIDL service //
    ///////////////////////////////

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName,
                                        binder: IBinder) {
            Log.i(TAG, "onServiceConnected")
            service = IPrintoidCommunicationInterface.Stub.asInterface(binder)
            btn_request_profiles.isEnabled = true
        }

        override fun onServiceDisconnected(className: ComponentName) {
            Log.i(TAG, "onServiceDisconnected")
            service = null
            btn_request_profiles.isEnabled = false
        }
    }

    private fun bindToService() {
        if (!isBound) {
            val printoidPn = getPrintoidPackageName()
            if (TextUtils.isEmpty(printoidPn)) {
                Log.w(TAG, "Printoid is not installed on this device")
                return
            }

            val intent = Intent()
            intent.setClassName(printoidPn!!, SERVICE_CLASSNAME)
            intent.action = REMOTE_ACTION

            val result = bindService(intent, connection, Context.BIND_AUTO_CREATE)
            Log.i(TAG, "bindService.result: $result")

            isBound = true
        }
    }

    ////////////////////////
    // Package management //
    ////////////////////////

    private fun getPrintoidPackageName(): String? = when {
                isInstalled(PRINTOID_PREMIUM_PN) -> PRINTOID_PREMIUM_PN
                isInstalled(PRINTOID_PRO_PN) -> PRINTOID_PRO_PN
                isInstalled(PRINTOID_LITE_PN) -> PRINTOID_LITE_PN
                else -> null
            }

    private fun isInstalled(packageName: String): Boolean =
        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }

}
