# 项目3
# 数据存储应用开发

## Week9  
## 数据存储（一）
---

### 实验内容

#### 要求  
* Figure 1：首次进入，呈现创建密码界面。  
 ![preview](https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images1/fig1.jpg)
* Figure 2：若密码不匹配，弹出Toast提示。  
 ![preview](https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images1/fig2.jpg) 
* Figure 3：若密码为空，弹出Toast提示。  
 ![preview](https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images1/fig3.jpg) 
* Figure 4：退出后第二次进入呈现输入密码界面。  
 ![preview](https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images1/fig4.jpg) 
* Figure 5：若密码不正确，弹出Toast提示。  
 ![preview](https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images1/fig5.jpg)
* Figure 6：文件加载失败，弹出Toast提示。  
 ![preview](https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images1/fig6.jpg) 
* Figure 7：成功保存文件，弹出Toast提示。  
 ![preview](https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images1/fig7.jpg) 
* Figure 8：成功导入文件，弹出Toast提示。  
 ![preview](https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images1/fig8.jpg) 
###
1.  如Figure 1至Figure 8所示，本次实验演示应用包含两个Activity。 
2.  首先是密码输入Activity：
    * 若应用首次启动，则界面呈现出两个输入框，分别为新密码输入框和确认密码输入框。  
    * 输入框下方有两个按钮：  
        - OK按钮点击后：  
            + 若New Password为空，则发出Toast提示。见Figure 3。
            + 若New Password与Confirm Password不匹配，则发出Toast提示，见Figure 2。
            + 若两密码匹配，则保存此密码，并进入文件编辑Activity。
        - CLEAR按钮点击后：清楚两输入框的内容。  
    * 完成创建密码后，退出应用再进入应用，则只呈现一个密码输入框，见Figure 4。
        - 点击OK按钮后，若输入的密码与之前的密码不匹配，则弹出Toast提示，见Figure 5。
        - 点击CLEAR按钮后，清除密码输入框的内容。
    * **出于演示和学习的目的，本次实验我们使用SharedPreferences来保存密码。但实际应用中不会使用这种方式来存储敏感信息，而是采用更安全的机制。见[这里](http://stackoverflow.com/questions/1925486/android-storing-username-and-password)和[这里](http://stackoverflow.com/questions/785973/what-is-the-most-appropriate-way-to-store-user-settings-in-android-application/786588)。**
3.  文件编辑Activity：
    * 界面底部有三个按钮，高度一致，顶对齐，按钮水平均匀分布，三个按钮上方除ActionBar和StatusBar之外的全部空间由一个EditText占据（保留margin）。EditText内的文字竖直方向置顶，左对齐。
    * 在编辑区域输入任意内容，点击SAVE按钮后能保存到指定文件（文件名随意）。成功保存后，弹出Toast提示，见Figure 8。
    * 点击CLEAR按钮，能清空编辑区域的内容。
    * 点击LOAD按钮，能够从同一文件导入内容，并显示到编辑框中。若成功导入，则弹出Toast提示。见Figure 7.若读取文件过程中出现异常（如文件不存在），则弹出Toast提示。见Figure 6.
4.  特殊要求：进入文件编辑Activity后，若点击返回按钮，则直接返回Home界面，不再返回密码输入Activity。

---

## Week11 
## 数据存储（二）
---

### 实验内容

实现一个评论应用，本次实验虽然号称是（二），但是和（一）无法合并到同一个项目当中，因此本实验应当新建一个项目，而不是在（一）的基础上继续开发。

#### 要求  

<table>
    <tr>
        <td ><img src="https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images2/fig1.1.jpg" >点击Login切换到登录界面</td>
        <td ><img src="https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images2/fig1.2.jpg"  >图1.2 若Username为空，则发出Toast提示</td>
    </tr>
    <tr>
        <td><img src="https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images2/fig1.3.jpg"  >图1.3 若Password为空，则发出Toast提示</td>
        <td ><img src="https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images2/fig1.4.jpg"  >图1.4 若Username不存在，则发出Toast提示 </td>
    </tr>
    <tr>
        <td><img src="https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images2/fig1.5.jpg"   > 图1.5 若密码不正确，则发出Toast提示</td>
        <td></td>
    </tr>
    <tr>
        <td><img src="https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images2/fig2.1.jpg"  >图2.1 点击Register切换到注册页面</td>
        <td ><img src="https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images2/fig2.2.jpg"  >图2.2 若Username为空，则发出Toast提示 </td>
    </tr>
    <tr>
        <td><img src="https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images2/fig2.3.jpg"  >图2.3 若New Password为空，则发出Toast提示</td>
        <td ><img src="https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images2/fig2.4.jpg"  >图2.4 若New Password与Confirm Password不匹配，则发出Toast提示 </td>
    </tr>
    <tr>
        <td><img src="https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images2/fig2.5.jpg"  >图2.5 若Username已经存在，则发出Toast提示</td>
        <td > </td>
    </tr>  
    <tr>
        <td><img src="https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images2/fig3.1.jpg"  >图3.1 评论页面</td>
        <td ><img src="https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images2/fig3.2.jpg"  >图3.2 若EditText为空，则发出Toast提示 </td>
    </tr>  
    <tr>
        <td><img src="https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images2/fig3.3.jpg"  >图3.3 短按评论：弹出对话框，显示该评论的用户以及通讯录中该用户的电话号码</td>
        <td ><img src="https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images2/fig3.4.jpg"  >图3.4 短按评论：弹出对话框，显示该评论的用户以及通讯录中该用户的电话号码 </td>
    </tr>  
    <tr>
        <td><img src="https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images2/fig3.5.jpg"  >图3.5 弹出是否删除的对话框</td>
        <td ><img src="https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images2/fig3.6.jpg"  >图3.6 弹出是否举报的对话框 </td>
    </tr>  
    <tr>
        <td><img src="https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images2/fig4.1.jpg"  >图4.1 进入手机图库进行图片选择</td>
        <td ><img src="https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images2/fig4.2.jpg"  >图4.2 ImageView显示本次选择的图片 </td>
    </tr>  
    <tr>
        <td><img src="https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images2/fig4.3.jpg"  >图4.3 在评论页面，每条Item应当正确显示用户的头像</td>
        <td > </td>
    </tr>  
</table>


* #### 技术要求： 
    1. 使用SQLite数据库保存用户的相关信息和评论的相关信息，使得每次运行程序都可以使用数据库进行用户的登陆与注册，以及显示数据库中的评论；
    2. 使用ContentProvider来获取对应用户的电话号码；
* #### 功能要求：  
    1. 如图1至图8所示，本次实验演示应包含2个Activity。
    2. 首页Activity包含登录功能和注册功能，通过radioButton在两个页面进行切换,在登陆界面输入正确的用户名和密码后跳转到评论页面。
    3. 评论Activity,界面由ListView、EditText和Button组成，ListView中展示数据库中保存的评论信息，在EditText写评论，点击Send按钮发送评论。
    4. 首页Activity：
        * 应用启动时，界面初始化为登录界面，通过Login和Register两个RadioButton进行登录与注册之间的切换。
        * 点击Login切换到登录界面（见图1.1），可以保留注册界面时的Username，但不保存密码：
            - OK按钮点击后：
                + 若Username为空，则发出Toast提示。见图1.2.
                + 若Password为空，则发出Toast提示。见图1.3.
                + 若Username不存在，则发出Toast提示。见图1.4.
                + 若密码不正确，则发出Toast提示。见图1.5.
            - CLEAR按钮点击后：清除两个输入框的内容。
        * 点击Register切换到注册页面（见图2.1），可以保留登录界面时的Username，但不保存密码，在输入框和RadioButto之间存在一个头像ImageView，水平居中：
            - OK按钮点击后：
                + 若Username为空，则发出Toast提示。见图2.2.
                + 若New Password为空，则发出Toast提示。见图2.3.
                + 若New Password与Confirm Password不匹配，则发出Toast提示。见图2.4.
                + 若Username已经存在，则发出Toast提示。见图2.5. 
            - CLEAR按钮点击后：清除三个输入框的内容。
    5. 评论页面：
        * 界面底部有一个EditText和一个按钮，高度一致，EditText占据按钮左边的全部空间。上方的全部剩余空间由一个ListView占据（保留margin）。见图3.1.
        * ListView中的每条Item，包含了头像、点赞按钮这两个ImageView和用户名、评论时间、评论内容、点赞数这4个TextView。
            - 用户名、评论时间、评论内容在头像的右边。
            - 点赞按钮在Item的最右边，而且在用户名+评论时间的总高度上处于竖直方向上居中，**注意：总高度不包括评论占据的高度**
            - 点赞数在点赞按钮的左边，竖直方向居中要求同点赞按钮。
            - **以下样式供参考，不做强制要求，但要求至少美观：**
                + Item整体margin：10dp，
                + 头像width、hight：40sp，
                + 用户名textColor：#7a7a7a、textSize：20sp
                + 评论时间textColor：#7a7a7a、textSize：10sp
                + 评论textColor：#3e3e3e、textSize：20sp
                + 点赞数textSize：15sp 
        * 点击EditText写评论
        * 点击Send按钮发送评论
            - 若EditText为空，则发出Toast提示。如图3.2.
            - 若EditText不为空，则发送评论，在数据库和ListView中添加新评论。
        * ListView中的Item点击事件：
            - 短按评论：弹出对话框，显示该评论的用户以及通讯录中该用户的电话号码。如图3.3和图3.4.
            - 长按评论：
                + 若该评论为当前用户发送的，弹出是否删除的对话框,若选择了Yes，则删除该条评论并更新数据库和ListView。如图3.5.
                + 若该评论不为当前用户发送的，弹出是否举报的对话框，若选择了Yes，则弹出Toast提示，不需做任何数据库和ListView的更改。如图3.6.

    **附加内容（加分项，本次实验与（一）合计100分，加分项每项占10分）**
    
    1. **头像**
        在用户注册页面可以选择用户头像，ImageView初始化为图add![preview](https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images2/add.png)，如图2.1。点击ImageView，进入手机图库进行图片选择。如图4.1.
        * 如果正确选择了一张图片，则ImageView显示本次选择的图片。如图4.2.
        * 如果没有正确选择图片（如在图片选择页面点击了取消或按了手机的BACK键），则ImageView保留本次点击事件发生前的状态，如初始的＋号图片，如图4.1，或者是上一个被正确选择的图像。如图4.2.
        在评论页面，每条Item应当正确显示用户的头像，如果用户没有在注册页面选择头像，则使用默认头像![preview](https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images2/me.png)。如图4.3.
    2. **点赞**
    在评论界面，点赞按钮可以被点击，点赞数可以正常统计，用户点赞状态可以被正常记录，白色的未点赞状态![preview](https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images2/white.png)经用户点击后变为红色的点赞状态![preview](https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab3/images2/red.png)，点赞数加1；再次点击点赞按钮可取消点赞，点赞数减1.要求用数据库记录点赞的信息，使得应用重启后用户的点赞状态，评论的点赞数可以正常显示，注意：用户的对每条评论的点赞状态是唯一的，即不同用户对每条评论的点赞状态应当分开记录，同一用户对不同评论的点赞状态也应当分开记录。同理，每条评论的点赞数也应当分开记录。请参考demo自行体会。

    **对附加内容的补充（不想做加分项的看这里）**

    1. **头像**
        在用户注册页面的ImageView显示为默认头像，且不需要添加任何的点击事件监听器，在评论页面的用户头像也使用默认头像。
    2. **点赞**
        不需要为点赞按钮添加点击事件监听器，关于点赞状态和点赞数使用随机数进行生成即可，也不要求用数据库记录点赞状态和点赞数。  

    3. **虽然点击事件的逻辑可以不做，但是界面的样式是必须按照前文做的！**
