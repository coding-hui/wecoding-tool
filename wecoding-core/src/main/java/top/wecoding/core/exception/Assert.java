/*
 * Copyright (c) 2022. WeCoding (wecoding@yeah.net).
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/lgpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.wecoding.core.exception;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import top.wecoding.core.exception.base.BaseException;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Assertion utility class
 *
 * @author liuyuhui
 * @qq 1515418211
 */
public abstract class Assert {

    /**
     * 断言一个布尔表达式，抛出{@code ArgumentException}
     * <pre class="code">Assert.state(id == null, "The id property must not already be initialized");</pre>
     *
     * @param expression 布尔表达式
     * @param message    异常消息
     * @throws ArgumentException if {@code expression} is {@code false}
     */
    public static void state(boolean expression, String message) {
        if (!expression) {
            throw new ArgumentException(message);
        }
    }

    /**
     * 断言一个布尔表达式，抛出{@code ArgumentException}
     * <pre class="code">
     * Assert.isTrue(i &gt; 0, "The value must be greater than zero");
     * </pre>
     *
     * @param expression       布尔表达式
     * @param errorMsgTemplate 消息模板，变量用{}代替
     * @param params           消息参数
     * @throws ArgumentException if {@code expression} is {@code false}
     */
    public static void state(boolean expression, String errorMsgTemplate, Object... params) throws ArgumentException {
        isTrue(expression, () -> new ArgumentException(BaseException.wrapErrorMessage(errorMsgTemplate, params)));
    }

    /**
     * 断言一个布尔表达式，抛出{@code ArgumentException}
     * <pre class="code">
     * Assert.state(entity.getId() == null,
     *     () -&gt; new ArgumentException("ID for entity " + entity.getName() + " must not already be initialized"));
     * </pre>
     *
     * @param expression        布尔表达式
     * @param exceptionSupplier 在断言失败时抛出的异常
     * @throws E if {@code expression} is {@code false}
     */
    public static <E extends Throwable> void state(boolean expression, Supplier<? extends E> exceptionSupplier) throws E {
        if (!expression) {
            throw nullExceptionSafeGet(exceptionSupplier);
        }
    }

    /**
     * 断言一个布尔表达式，抛出{@code ArgumentException}
     *
     * @param expression 布尔表达式
     * @param message    异常消息
     * @throws ArgumentException if {@code expression} is {@code false}
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new ArgumentException(message);
        }
    }

    /**
     * 断言一个布尔表达式，抛出{@code ArgumentException}
     * <pre class="code">
     * Assert.isTrue(i &gt; 0, "The value must be greater than zero");
     * </pre>
     *
     * @param expression       布尔表达式
     * @param errorMsgTemplate 消息模板，变量用{}代替
     * @param params           参数
     * @throws ArgumentException if {@code expression} is {@code false}
     */
    public static void isTrue(boolean expression, String errorMsgTemplate, Object... params) throws ArgumentException {
        isTrue(expression, () -> new ArgumentException(BaseException.wrapErrorMessage(errorMsgTemplate, params)));
    }

    /**
     * 断言一个布尔表达式，抛出{@code ArgumentException}
     * <pre class="code">
     * Assert.state(entity.getId() == null,
     *     () -&gt; new ArgumentException("ID for entity " + entity.getName() + " must not already be initialized"));
     * </pre>
     *
     * @param expression        布尔表达式
     * @param exceptionSupplier 在断言失败时抛出的异常
     * @throws E if {@code expression} is {@code false}
     */
    public static <E extends Throwable> void isTrue(boolean expression, Supplier<? extends E> exceptionSupplier) throws E {
        if (!expression) {
            throw nullExceptionSafeGet(exceptionSupplier);
        }
    }

    /**
     * 断言一个对象是{@code null}
     * <pre class="code">Assert.isNull(value, "The value must be null");</pre>
     *
     * @param object  对象
     * @param message 错误消息
     * @throws ArgumentException if the object is not {@code null}
     */
    public static void isNull(@Nullable Object object, String message) {
        if (object != null) {
            throw new ArgumentException(message);
        }
    }

    /**
     * 断言一个对象是{@code null}
     * <pre class="code">
     * Assert.isNull(value, () -&gt; new ArgumentException("The value '" + value + "' must be null"));
     * </pre>
     *
     * @param object            对象
     * @param exceptionSupplier 在断言失败时抛出的异常
     * @throws E if the object is not {@code null}
     */
    public static <E extends Throwable> void isNull(@Nullable Object object, Supplier<? extends E> exceptionSupplier) throws E {
        if (object != null) {
            throw nullExceptionSafeGet(exceptionSupplier);
        }
    }

    /**
     * 断言一个对象不是{@code null}
     * <pre class="code">Assert.notNull(clazz, "The class must not be null");</pre>
     *
     * @param object  对象
     * @param message 错误消息
     * @throws ArgumentException if the object is {@code null}
     */
    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new ArgumentException(message);
        }
    }

    /**
     * 断言一个对象不是{@code null}
     * <pre class="code">
     * Assert.isNull(value, () -&gt; new ArgumentException("The value '" + value + "' must not be null"));
     * </pre>
     *
     * @param object            对象
     * @param exceptionSupplier 在断言失败时抛出的异常
     * @throws E if the object is {@code null}
     */
    public static <E extends Throwable> void notNull(@Nullable Object object, Supplier<? extends E> exceptionSupplier) throws E {
        if (object == null) {
            throw nullExceptionSafeGet(exceptionSupplier);
        }
    }

    /**
     * 断言给定的String不为空.
     * <pre class="code">Assert.hasLength(name, "Name must not be empty");</pre>
     *
     * @param text    字符串
     * @param message 异常信息
     * @throws ArgumentException if the text is empty
     * @see StrUtil#isBlank
     */
    public static void notBlank(@Nullable String text, String message) {
        if (StrUtil.isBlank(text)) {
            throw new ArgumentException(message);
        }
    }

    /**
     * 断言给定的String不为空.
     * <pre class="code">
     * Assert.hasLength(account.getName(),
     *     () -&gt; "Name for account '" + account.getId() + "' must not be empty");
     * </pre>
     *
     * @param text              字符串
     * @param exceptionSupplier 在断言失败时抛出的异常
     * @throws E if the text is empty
     * @see StrUtil#isBlank
     */
    public static <E extends Throwable> void notBlank(@Nullable String text, Supplier<? extends E> exceptionSupplier) throws E {
        if (StrUtil.isBlank(text)) {
            throw nullExceptionSafeGet(exceptionSupplier);
        }
    }

    /**
     * 断言是否为子串
     * <pre class="code">Assert.doesNotContain(name, "rod", "Name must not contain 'rod'");</pre>
     *
     * @param textToSearch 被搜索的字符串
     * @param substring    被检查的子串
     * @param message      异常信息
     * @throws ArgumentException if the text contains the substring
     */
    public static void doesNotContain(@Nullable String textToSearch, String substring, String message) {
        if (StrUtil.contains(textToSearch, substring)) {
            throw new ArgumentException(message);
        }
    }

    /**
     * 断言是否为子串
     * <pre class="code">
     * Assert.doesNotContain(name, forbidden, () -&gt; "Name must not contain '" + forbidden + "'");
     * </pre>
     *
     * @param textToSearch      被搜索的字符串
     * @param substring         被检查的子串
     * @param exceptionSupplier 在断言失败时抛出的异常
     * @throws IllegalArgumentException if the text contains the substring
     */
    public static <E extends Throwable> void doesNotContain(@Nullable String textToSearch, String substring, Supplier<? extends E> exceptionSupplier) throws E {
        if (StrUtil.contains(textToSearch, substring)) {
            throw nullExceptionSafeGet(exceptionSupplier);
        }
    }

    /**
     * 断言数组不为空
     *
     * @param array   检查数组
     * @param message 异常信息
     * @throws ArgumentException if the object array is {@code null} or contains no elements
     */
    public static <T> void notEmpty(@Nullable T[] array, String message) {
        if (ArrayUtil.isEmpty(array)) {
            throw new ArgumentException(message);
        }
    }

    /**
     * 断言数组不为空
     *
     * @param array             检查数组
     * @param exceptionSupplier 在断言失败时抛出的异常
     * @throws E if the object array is {@code null} or contains no elements
     */
    public static <T, E extends Throwable> void notEmpty(@Nullable T[] array, Supplier<? extends E> exceptionSupplier) throws E {
        if (ArrayUtil.isEmpty(array)) {
            throw nullExceptionSafeGet(exceptionSupplier);
        }
    }

    /**
     * 断言数组不包含{@code null}元素
     *
     * @param array   检查数组
     * @param message 异常信息
     * @throws ArgumentException if the object array contains a {@code null} element
     */
    public static <T> void noNullElements(@Nullable T[] array, String message) {
        if (ArrayUtil.hasNull(array)) {
            throw new ArgumentException(message);
        }
    }

    /**
     * 断言数组不包含{@code null}元素
     *
     * @param array             检查数组
     * @param exceptionSupplier 在断言失败时抛出的异常
     * @throws E if the object array contains a {@code null} element
     */
    public static <T, E extends Throwable> void noNullElements(@Nullable T[] array, Supplier<? extends E> exceptionSupplier) throws Throwable {
        if (ArrayUtil.hasNull(array)) {
            throw nullExceptionSafeGet(exceptionSupplier);
        }
    }

    /**
     * 断言集合不空
     *
     * @param collection 集合
     * @param message    异常信息
     * @throws ArgumentException if the collection is {@code null} or
     *                           contains no elements
     */
    public static void notEmpty(@Nullable Collection<?> collection, String message) {
        if (CollectionUtil.isEmpty(collection)) {
            throw new ArgumentException(message);
        }
    }

    /**
     * 断言集合不空
     *
     * @param collection        集合
     * @param exceptionSupplier 在断言失败时抛出的异常
     * @throws E if the collection is {@code null} or contains no elements
     */
    public static <E extends Throwable> void notEmpty(@Nullable Collection<?> collection, Supplier<? extends E> exceptionSupplier) throws E {
        if (CollectionUtil.isEmpty(collection)) {
            throw nullExceptionSafeGet(exceptionSupplier);
        }
    }

    /**
     * 断言集合不包含{@code null}元素
     *
     * @param collection 集合
     * @param message    异常信息
     * @throws ArgumentException if the collection contains a {@code null} element
     */
    public static void noNullElements(@Nullable Collection<?> collection, String message) {
        if (CollectionUtil.hasNull(collection)) {
            throw new ArgumentException(message);
        }
    }

    /**
     * 断言集合不包含{@code null}元素
     *
     * @param collection        集合
     * @param exceptionSupplier 在断言失败时抛出的异常
     * @throws E if the collection contains a {@code null} element
     */
    public static <E extends Throwable> void noNullElements(@Nullable Collection<?> collection, Supplier<? extends E> exceptionSupplier) throws Throwable {
        if (CollectionUtil.hasNull(collection)) {
            throw nullExceptionSafeGet(exceptionSupplier);
        }
    }

    /**
     * 断言 Map 不空
     *
     * @param map     Map
     * @param message 异常信息
     * @throws ArgumentException if the map is {@code null} or contains no entries
     */
    public static void notEmpty(@Nullable Map<?, ?> map, String message) {
        if (MapUtil.isEmpty(map)) {
            throw new ArgumentException(message);
        }
    }

    /**
     * 断言 Map 不空
     *
     * @param map               Map
     * @param exceptionSupplier 在断言失败时抛出的异常
     * @throws E if the map is {@code null} or contains no entries
     */
    public static <E extends Throwable> void notEmpty(@Nullable Map<?, ?> map, Supplier<? extends E> exceptionSupplier) throws E {
        if (MapUtil.isEmpty(map)) {
            throw nullExceptionSafeGet(exceptionSupplier);
        }
    }

    /**
     * 断言所提供的对象是否是给定类的实例.
     * <pre class="code">Assert.instanceOf(Foo.class, foo, "Foo expected");</pre>
     *
     * @param type    被检查对象匹配的类型
     * @param obj     被检查对象
     * @param message 异常信息
     * @throws ArgumentException if the object is not an instance of type
     */
    public static void isInstanceOf(Class<?> type, @Nullable Object obj, String message) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            instanceCheckFailed(type, obj, message);
        }
    }

    /**
     * 断言所提供的对象是否是给定类的实例.
     *
     * @param type              被检查对象匹配的类型
     * @param obj               被检查对象
     * @param exceptionSupplier 在断言失败时抛出的异常
     * @throws ArgumentException if the object is not an instance of type
     */
    public static <E extends Throwable> void isInstanceOf(Class<?> type, @Nullable Object obj, Supplier<? extends E> exceptionSupplier) throws Throwable {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw nullExceptionSafeGet(exceptionSupplier);
        }
    }

    /**
     * 断言所提供的对象是否是给定类的实例.
     * <pre class="code">Assert.instanceOf(Foo.class, foo);</pre>
     *
     * @param type 被检查对象匹配的类型
     * @param obj  被检查对象
     * @throws ArgumentException if the object is not an instance of type
     */
    public static void isInstanceOf(Class<?> type, @Nullable Object obj) {
        isInstanceOf(type, obj, "");
    }

    /**
     * 断言 {@code superType.isAssignableFrom(subType)} 是 {@code true}.
     * <pre class="code">Assert.isAssignable(Number.class, myClass, "Number expected");</pre>
     *
     * @param superType 需要检查的父类或接口
     * @param subType   需要检查的子类
     * @param message   异常信息
     * @throws ArgumentException if the classes are not assignable
     */
    public static void isAssignable(Class<?> superType, @Nullable Class<?> subType, String message) {
        notNull(superType, "Super type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            assignableCheckFailed(superType, subType, message);
        }
    }

    /**
     * 断言 {@code superType.isAssignableFrom(subType)} 是 {@code true}.
     *
     * @param superType         需要检查的父类或接口
     * @param subType           需要检查的子类
     * @param exceptionSupplier 在断言失败时抛出的异常
     * @throws ArgumentException if the classes are not assignable
     */
    public static <E extends Throwable> void isAssignable(Class<?> superType, @Nullable Class<?> subType, Supplier<? extends E> exceptionSupplier) throws Throwable {
        notNull(superType, "Super type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw nullExceptionSafeGet(exceptionSupplier);
        }
    }

    /**
     * 断言 {@code superType.isAssignableFrom(subType)} 是 {@code true}.
     * <pre class="code">Assert.isAssignable(Number.class, myClass);</pre>
     *
     * @param superType 需要检查的父类或接口
     * @param subType   需要检查的子类
     * @throws ArgumentException if the classes are not assignable
     */
    public static void isAssignable(Class<?> superType, Class<?> subType) {
        isAssignable(superType, subType, "");
    }

    private static void instanceCheckFailed(Class<?> type, @Nullable Object obj, @Nullable String msg) {
        String className = (obj != null ? obj.getClass().getName() : "null");
        String result = "";
        boolean defaultMessage = true;
        if (StringUtils.hasLength(msg)) {
            if (endsWithSeparator(msg)) {
                result = msg + " ";
            } else {
                result = messageWithTypeName(msg, className);
                defaultMessage = false;
            }
        }
        if (defaultMessage) {
            result = result + ("Object of class [" + className + "] must be an instance of " + type);
        }
        throw new ArgumentException(result);
    }

    private static void assignableCheckFailed(Class<?> superType, @Nullable Class<?> subType, @Nullable String msg) {
        String result = "";
        boolean defaultMessage = true;
        if (StringUtils.hasLength(msg)) {
            if (endsWithSeparator(msg)) {
                result = msg + " ";
            } else {
                result = messageWithTypeName(msg, subType);
                defaultMessage = false;
            }
        }
        if (defaultMessage) {
            result = result + (subType + " is not assignable to " + superType);
        }
        throw new ArgumentException(result);
    }

    private static boolean endsWithSeparator(String msg) {
        return (msg.endsWith(":") || msg.endsWith(";") || msg.endsWith(",") || msg.endsWith("."));
    }

    private static String messageWithTypeName(String msg, @Nullable Object typeName) {
        return msg + (msg.endsWith(" ") ? "" : ": ") + typeName;
    }

    private static <E extends Throwable> E nullExceptionSafeGet(@Nullable Supplier<? extends E> exceptionSupplier) {
        return (exceptionSupplier != null ? exceptionSupplier.get() : null);
    }

}
