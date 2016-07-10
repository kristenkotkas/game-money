package rural.gamemoney;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class PaymentButton extends Button {

    private String buttonId;
    private Payment currentActivity;

    public PaymentButton(Context context) {
        super(context);
        initListener();
    }

    public PaymentButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initListener();
    }

    public PaymentButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initListener();
    }

    void setCurrentActivity(Payment act) {
        this.currentActivity = act;
    }

    private void initListener() {
        buttonId = this.getText().toString();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                currentActivity.transactionAmount(""+buttonId);
            }
        });
    }
}
