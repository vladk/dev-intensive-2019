package ru.skillbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extensions.*
import ru.skillbranch.devintensive.models.BaseMessage
import ru.skillbranch.devintensive.models.Chat
import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun test_instance() {
        val user = User("2", "John", "Cena", null)
        println(user)
    }

    @Test
    fun test_parse_fullname() {
        assertEquals("John" to "Wick", Utils.parseFullName("John Wick"))
        assertEquals(null to null, Utils.parseFullName(null))
        assertEquals(null to null, Utils.parseFullName(" "))
        assertEquals(null to null, Utils.parseFullName(""))
        assertEquals("John" to "Wick", Utils.parseFullName("John    Wick"))
        assertEquals("John" to "Wick", Utils.parseFullName("   John    Wick"))
        assertEquals("John" to "Wick", Utils.parseFullName("   John    Wick   "))
        assertEquals(null to null, Utils.parseFullName(null))
        assertEquals(null to null, Utils.parseFullName(""))
        assertEquals(null to null, Utils.parseFullName(" "))
        assertEquals("John" to null, Utils.parseFullName("John"))
        assertEquals(null to null, Utils.parseFullName(" "))
    }

    @Test
    fun test_abstract_factory() {
        val user = User.makeUser("Евгений Иванов")
        val textMessage =
            BaseMessage.makeMessage(user, Chat("0"), Date(), "text", "any text message")
        val imageMessage =
            BaseMessage.makeMessage(
                user,
                Chat("0"),
                Date().add(-2, TimeUnits.HOUR),
                "image",
                "https://anyurl.com",
                true
            )

        assertEquals("Евгений отправил сообщение \"any text message\" только что", textMessage.formatMessage())
        assertEquals("Евгений получил изображение \"https://anyurl.com\" 2 часа назад", imageMessage.formatMessage())
    }

    @Test
    fun test_date_format() {
        val date =
            Date.from(Calendar.getInstance(Locale("ru")).apply { set(2019, Calendar.JUNE, 27, 14, 0, 0) }.toInstant())
        assertEquals("14:00:00 27.06.19", date.format())
        assertEquals("14:00", date.format("HH:mm"))
    }

    @Test
    fun test_date_add() {
        val instant = Calendar.getInstance().apply { set(2019, Calendar.JUNE, 27, 14, 0, 0) }.toInstant()
        val date = Date.from(instant)
        val date2 = Date.from(instant)
        assertEquals("14:00:02 27.06.19", date.add(2, TimeUnits.SECOND).format())
        assertEquals("14:00:00 23.06.19", date2.add(-4, TimeUnits.DAY).format())
    }

    @Test
    fun test_initial() {
        assertEquals("JD", Utils.toInitials("john", "doe"))
        assertEquals("JD", Utils.toInitials("John", "Doe"))
        assertEquals("J", Utils.toInitials("John", null))
        assertEquals(null, Utils.toInitials(null, null))
        assertEquals(null, Utils.toInitials(" ", ""))
    }

    @Test
    fun transliteration() {
        assertEquals("Ivan Stereotipov", Utils.transliteration("Иван Стереотипов"))
        assertEquals("Amazing_Petr", Utils.transliteration("Amazing Петр", "_"))

        assertEquals("Zh Zh", Utils.transliteration("Ж Ж"))
        assertEquals("ZhZh", Utils.transliteration("ЖЖ"))
        assertEquals("AbrAKadabra", Utils.transliteration("AbrAKadabra"))
        assertEquals("StraNNIi NikVash'e", Utils.transliteration("СтраННЫй НикВаще"))
    }

    @Test
    fun test_humanizeDiff() {
        assertEquals("через несколько секунд", Date().humanizeDiff(Date().add(-2, TimeUnits.SECOND)))
        assertEquals("2 часа назад", Date().add(-2, TimeUnits.HOUR).humanizeDiff())
        assertEquals("5 дней назад", Date().add(-5, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 2 минуты", Date().add(2, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("через 7 дней", Date().add(7, TimeUnits.DAY).humanizeDiff())
        assertEquals("более года назад", Date().add(-400, TimeUnits.DAY).humanizeDiff())
        assertEquals("более чем через год", Date().add(400, TimeUnits.DAY).humanizeDiff())


        assertEquals("через 2 минуты", Date().add(2, TimeUnits.MINUTE).humanizeDiff())

        assertEquals("минуту назад", Date().add(-1, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("2 минуты назад", Date().add(-2, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("3 минуты назад", Date().add(-3, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("5 минут назад", Date().add(-5, TimeUnits.MINUTE).humanizeDiff())

        assertEquals("час назад", Date().add(-1, TimeUnits.HOUR).humanizeDiff())
        assertEquals("2 часа назад", Date().add(-2, TimeUnits.HOUR).humanizeDiff())
        assertEquals("3 часа назад", Date().add(-3, TimeUnits.HOUR).humanizeDiff())
        assertEquals("4 часа назад", Date().add(-4, TimeUnits.HOUR).humanizeDiff())
        assertEquals("5 часов назад", Date().add(-5, TimeUnits.HOUR).humanizeDiff())

        assertEquals("день назад", Date().add(-1, TimeUnits.DAY).humanizeDiff())
        assertEquals("4 дня назад", Date().add(-4, TimeUnits.DAY).humanizeDiff())
        assertEquals("5 дней назад", Date().add(-5, TimeUnits.DAY).humanizeDiff())
        assertEquals("100 дней назад", Date().add(-100, TimeUnits.DAY).humanizeDiff())





        assertEquals("несколько секунд назад", Date().humanizeDiff(Date().add(34, TimeUnits.SECOND)))
        assertEquals("минуту назад", Date().humanizeDiff(Date().add(61, TimeUnits.SECOND)))
        assertEquals("5 минут назад", Date().humanizeDiff(Date().add(5, TimeUnits.MINUTE)))
        assertEquals("20 дней назад", Date().humanizeDiff(Date().add(20, TimeUnits.DAY)))
        assertEquals("90 дней назад", Date().humanizeDiff(Date().add(90, TimeUnits.DAY)))
        assertEquals("через несколько секунд", Date().humanizeDiff(Date().add(-13, TimeUnits.SECOND)))
        assertEquals("через минуту", Date().humanizeDiff(Date().add(-63, TimeUnits.SECOND)))
        assertEquals("через минуту", Date().humanizeDiff(Date().add(-1, TimeUnits.MINUTE)))
        assertEquals("через 29 дней", Date().humanizeDiff(Date().add(-29, TimeUnits.DAY)))
        assertEquals("только что", Date().humanizeDiff(Date().add(0, TimeUnits.HOUR)))
        assertEquals(
            "через несколько секунд",
            Date().humanizeDiff(Date().add(-2, TimeUnits.SECOND))
        ) //несколько секунд назад
        assertEquals("2 часа назад", Date().add(-2, TimeUnits.HOUR).humanizeDiff()) //2 часа назад
        assertEquals("5 дней назад", Date().add(-5, TimeUnits.DAY).humanizeDiff()) //5 дней назад
        assertEquals("через 2 минуты", Date().add(2, TimeUnits.MINUTE).humanizeDiff()) //через 2 минуты
        assertEquals("через 7 дней", Date().add(7, TimeUnits.DAY).humanizeDiff()) //через 7 дней
        assertEquals("более года назад", Date().add(-400, TimeUnits.DAY).humanizeDiff()) //более года назад
        assertEquals("более чем через год", Date().add(400, TimeUnits.DAY).humanizeDiff()) //более чем через год
        assertEquals("только что", Date().humanizeDiff())
        assertEquals(
            "через несколько секунд",
            Date().humanizeDiff(Date().add(-2, TimeUnits.SECOND))
        ) //через несколько секунд
        assertEquals("2 часа назад", Date().add(-2, TimeUnits.HOUR).humanizeDiff())
        assertEquals("5 дней назад", Date().add(-5, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 2 минуты", Date().add(2, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("через 7 дней", Date().add(7, TimeUnits.DAY).humanizeDiff())
        assertEquals("более года назад", Date().add(-400, TimeUnits.DAY).humanizeDiff())
        assertEquals("более чем через год", Date().add(400, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 2 минуты", Date().add(2, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("минуту назад", Date().add(-1, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("2 минуты назад", Date().add(-2, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("3 минуты назад", Date().add(-3, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("5 минут назад", Date().add(-5, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("час назад", Date().add(-1, TimeUnits.HOUR).humanizeDiff())
        assertEquals("2 часа назад", Date().add(-2, TimeUnits.HOUR).humanizeDiff())
        assertEquals("3 часа назад", Date().add(-3, TimeUnits.HOUR).humanizeDiff())
        assertEquals("4 часа назад", Date().add(-4, TimeUnits.HOUR).humanizeDiff())
        assertEquals("5 часов назад", Date().add(-5, TimeUnits.HOUR).humanizeDiff())
        assertEquals("день назад", Date().add(-1, TimeUnits.DAY).humanizeDiff())
        assertEquals("4 дня назад", Date().add(-4, TimeUnits.DAY).humanizeDiff())
        assertEquals("5 дней назад", Date().add(-5, TimeUnits.DAY).humanizeDiff())

        assertEquals("100 дней назад", Date().add(-100, TimeUnits.DAY).humanizeDiff())
    }


    @Test
    fun test_builder() {
        val date = Date.from(Calendar.getInstance().apply { set(2019, Calendar.JUNE, 27, 14, 0, 0) }.toInstant())

        val user = User.Builder().id("1")
            .firstName("firstName")
            .lastName("lastName")
            .avatar("avatar")
            .rating(1)
            .respect(2)
            .lastVisit(date)
            .isOnline(true)
            .build()

        assertEquals("1", user.id)
        assertEquals("firstName", user.firstName)
        assertEquals("lastName", user.lastName)
        assertEquals("avatar", user.avatar)
        assertEquals(1, user.rating)
        assertEquals(2, user.respect)
        assertEquals(date, user.lastVisit)
        assertEquals(true, user.isOnline)

        val date2 = Date.from(Calendar.getInstance().apply { set(2018, Calendar.JUNE, 27, 14, 0, 0) }.toInstant())

        val user2 = User.Builder().id("2")
            .firstName("firstName2")
            .lastName("lastName2")
            .avatar("avatar2")
            .rating(3)
            .respect(4)
            .lastVisit(date2)
            .isOnline(false)
            .build()

        assertEquals("2", user2.id)
        assertEquals("firstName2", user2.firstName)
        assertEquals("lastName2", user2.lastName)
        assertEquals("avatar2", user2.avatar)
        assertEquals(3, user2.rating)
        assertEquals(4, user2.respect)
        assertEquals(date2, user2.lastVisit)
        assertEquals(false, user2.isOnline)

    }

    @Test
    fun test_plural() {
        assertEquals("1 секунду", TimeUnits.SECOND.plural(1))
        assertEquals("4 минуты", TimeUnits.MINUTE.plural(4))
        assertEquals("19 часов", TimeUnits.HOUR.plural(19))
        assertEquals("222 дня", TimeUnits.DAY.plural(222))
    }

    @Test
    fun test_truncate() {
        assertEquals("Bender Bending Ro...", "Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate())
        assertEquals("Bender Bending R...", "Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate(15))
        assertEquals("A", "A     ".truncate(3))
    }

    @Test
    fun test_stripHtml() {
        assertEquals("Образовательное IT-сообщество Skill Branch", "<p class=\"title\">Образовательное IT-сообщество Skill Branch</p>".stripHtml())
        assertEquals("Образовательное IT-сообщество Skill Branch", "<p>Образовательное       IT-сообщество Skill Branch</p>".stripHtml())
        assertEquals("Lorem ipsum blah Ha-ha-ha",
            "<div>\nLorem ipsum    blah <span style=\"color: #3333\">Ha-ha-ha</span>\n</div>".stripHtml())
    }
}
