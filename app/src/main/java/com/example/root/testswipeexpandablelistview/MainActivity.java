package com.example.root.testswipeexpandablelistview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.root.testswipeexpandablelistview.fragment.Fragment1;
import com.example.root.testswipeexpandablelistview.fragment.Fragment2;
import com.example.root.testswipeexpandablelistview.fragment.Fragment3;
import com.example.root.testswipeexpandablelistview.fragment.Fragment4;

public class MainActivity extends AppCompatActivity
{
    private static final int MENUITEM1 = 1;
    private static final int MENUITEM2 = 2;
    private static final int MENUITEM3 = 3;
    private static final int MENUITEM4 = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(new Fragment1());
        setTitle("expandable list view");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 虽然目前android还没有系统菜单，但是为了兼容到以后的版本，最好加上
        super.onCreateOptionsMenu(menu);
        // 第一个参数代表组号，android中你可以给菜单分组，以便快速地操作同一组的菜单。
        // 第二个参数代表Menu的唯一的ID号，可以自己指定，也可以让系统来自动分配，在响应菜单时你需要通过ID号来判断点击了哪个菜单。
        // 第三个参数代表Menu显示顺序的编号，编号小的显示在前面。
        // 第四个参数代表标题
        menu.add(0, MENUITEM1, MENUITEM1, "expandable list view");
        menu.add(0, MENUITEM2, MENUITEM2, "expandable list view in scroll");
        menu.add(0, MENUITEM3, MENUITEM3, "list view");
        menu.add(0, MENUITEM4, MENUITEM4, "list view in scroll");

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        setTitle(item.getTitle());

        switch (item.getItemId()) {
            // 响应每个菜单项(通过菜单项的ID)
            case MENUITEM1:
                replaceFragment(new Fragment1());
                break;
            case MENUITEM2:
                replaceFragment(new Fragment2());
                break;
            case MENUITEM3:
                replaceFragment(new Fragment3());
                break;
            case MENUITEM4:
                replaceFragment(new Fragment4());
                break;
            default:
                // 对没有处理的事件，交给父类来处理
                return super.onOptionsItemSelected(item);
        }
        // 返回true表示处理完菜单项的事件，不需要将该事件继续传播下去了
        return true;
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.layout_frame, fragment).commit();
    }
}
