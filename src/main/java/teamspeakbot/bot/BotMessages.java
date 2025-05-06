package teamspeakbot.bot;

public class BotMessages {

    public static String getWelcomeMessage(String userName) {
        return "Привет, " + userName + "! Добро пожаловать на сервер! 🎉\n" +
                "----------------------------------------\n" +
                "!Бот находится в разработке и может иметь некоторые ограничения!\n" +
                "----------------------------------------\n" +
                "🎮 Доступные игровые каналы:\n" +
                "🔹 Канал для Warhammer: Vermintide 2\n" +
                "🔹 Канал для PUBG\n" +
                "🔹 Канал для Dota 2\n" +
                "🔹 Канал для 7 days to die\n" +
                "🔹 Канал для Battlefiled\n" +
                "🔹 Канал для Delta Force\n" +
                "----------------------------------------\n" +
                "Чтобы включить режим Не беспокоить напиши !dnd\n" +
                "----------------------------------------\n" +
                "Чтобы узнать доступные команды напиши !help в канале, пока только в гостинной!";
    }

    public static final String HELP_MESSAGE =
            "📜 Доступные команды:\n" +
                    "🔹 !ping - Проверить, работает ли бот\n" +
                    "🔹 !serverinfo - Информация о сервере\n" +
                    "🔹 !dnd - Включение режима Не беспокоить\n" +
                    "🔹 !dndstatus - Включение режима Не беспокоить\n" +
                    "🔹 !rules - Правила сервера";

    public static final String SERVER_INFO_MESSAGE =
            "🏠 Информация о сервере:\n" +
                    "🔹 Название: Братья по Радуге\n" +
                    "🔹 Администратор: Maklein95";

    public static final String RULES_MESSAGE =
            "📜 Правила сервера:\n" +
                    "🔹 Можно оскорблять других участников!\n" +
                    "🔹 Не спамить в чат!\n" +
                    "🔹 Не пидрить!\n" +
                    "🔹 В катках ебать всех, можно жопой";

    public static final String DND_ENABLED = "✅ Режим 'Не беспокоить' включён.";
    public static final String DND_DISABLED = "❌ Режим 'Не беспокоить' отключён.";
    public static final String PING_MESSAGE = "Себя попингуй, ебло";

}
