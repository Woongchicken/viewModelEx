package com.example.viewmodelex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodelex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    companion object {
        const val TAG: String = "로그"
    }

    // 나중에 설정하기위해 lateinit
    lateinit var myNumberViewModel: MynumberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 뷰 모델 프로바이더를 통해 뷰모델 가져오기
        // 라이프사이클을 가지고 있는 녀석을 넣어줌. 즉 자기 자신
        // 우리가 가져오고 싶은 뷰모델 클래스를 넣어서 뷰모델을 가져오기
        myNumberViewModel = ViewModelProvider(this).get(MynumberViewModel::class.java)

        // 뷰모델이 가지고 있는 값의 변경사항을 관찰할 수 있는 라이브 데이터를 옵저빙한다
        myNumberViewModel.currentValue.observe(this, Observer {
            Log.d(TAG, "MainActivity - myNumberViewModel - currentValue 라이브 데이터 값 변경 : $it")
            binding.numberTextview.text = it.toString()
        })

        // 리스너 연결
        binding.plusButton.setOnClickListener (this)
        binding.minusButton.setOnClickListener (this)
    }

    override fun onClick(view: View?) {
        val userInput = binding.userinputEdittext.text.toString().toInt()
        // 뷰모델에 라이브데이터 값을 변경하는 메소드 실행
        when(view){
            binding.plusButton ->
                myNumberViewModel.updateValue(actionType = NumberActionType.PLUS, userInput)
            binding.minusButton ->
                myNumberViewModel.updateValue(actionType = NumberActionType.MINUS, userInput)
        }
    }

}