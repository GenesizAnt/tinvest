create table operation_type
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255),
    number      VARCHAR(255),
    description VARCHAR(255)
);

INSERT INTO operation_type (name, number, description)
VALUES ('OPERATION_TYPE_UNSPECIFIED', '0', 'Тип операции не определён.'),
       ('OPERATION_TYPE_INPUT', '1', 'Пополнение брокерского счёта.'),
       ('OPERATION_TYPE_BOND_TAX', '2', 'Удержание НДФЛ по купонам.'),
       ('OPERATION_TYPE_OUTPUT_SECURITIES', '3', 'Вывод ЦБ.'),
       ('OPERATION_TYPE_OVERNIGHT', '4', 'Доход по сделке РЕПО овернайт.'),
       ('OPERATION_TYPE_TAX', '5', 'Удержание налога.'),
       ('OPERATION_TYPE_BOND_REPAYMENT_FULL', '6', 'Полное погашение облигаций.'),
       ('OPERATION_TYPE_SELL_CARD', '7', 'Продажа ЦБ с карты.'),
       ('OPERATION_TYPE_DIVIDEND_TAX', '8', 'Удержание налога по дивидендам.'),
       ('OPERATION_TYPE_OUTPUT', '9', 'Вывод денежных средств.'),
       ('OPERATION_TYPE_BOND_REPAYMENT', '10', 'Частичное погашение облигаций.'),
       ('OPERATION_TYPE_TAX_CORRECTION', '11', 'Корректировка налога.'),
       ('OPERATION_TYPE_SERVICE_FEE', '12', 'Удержание комиссии за обслуживание брокерского счёта.'),
       ('OPERATION_TYPE_BENEFIT_TAX', '13', 'Удержание налога за материальную выгоду.'),
       ('OPERATION_TYPE_MARGIN_FEE', '14', 'Удержание комиссии за непокрытую позицию.'),
       ('OPERATION_TYPE_BUY', '15', 'Покупка ЦБ.'),
       ('OPERATION_TYPE_BUY_CARD', '16', 'Покупка ЦБ с карты.'),
       ('OPERATION_TYPE_INPUT_SECURITIES', '17', 'Перевод ценных бумаг из другого депозитария.'),
       ('OPERATION_TYPE_SELL_MARGIN', '18', 'Продажа в результате Margin-call.'),
       ('OPERATION_TYPE_BROKER_FEE', '19', 'Удержание комиссии за операцию.'),
       ('OPERATION_TYPE_BUY_MARGIN', '20', 'Покупка в результате Margin-call.'),
       ('OPERATION_TYPE_DIVIDEND', '21', 'Выплата дивидендов.'),
       ('OPERATION_TYPE_SELL', '22', 'Продажа ЦБ.'),
       ('OPERATION_TYPE_COUPON', '23', 'Выплата купонов.'),
       ('OPERATION_TYPE_SUCCESS_FEE', '24', 'Удержание комиссии SuccessFee.'),
       ('OPERATION_TYPE_DIVIDEND_TRANSFER', '25', 'Передача дивидендного дохода.'),
       ('OPERATION_TYPE_ACCRUING_VARMARGIN', '26', 'Зачисление вариационной маржи.'),
       ('OPERATION_TYPE_WRITING_OFF_VARMARGIN', '27', 'Списание вариационной маржи.'),
       ('OPERATION_TYPE_DELIVERY_BUY', '28', 'Покупка в рамках экспирации фьючерсного контракта.'),
       ('OPERATION_TYPE_DELIVERY_SELL', '29', 'Продажа в рамках экспирации фьючерсного контракта.'),
       ('OPERATION_TYPE_TRACK_MFEE', '30', 'Комиссия за управление по счёту автоследования.'),
       ('OPERATION_TYPE_TRACK_PFEE', '31', 'Комиссия за результат по счёту автоследования.'),
       ('OPERATION_TYPE_TAX_PROGRESSIVE', '32', 'Удержание налога по ставке 15%.'),
       ('OPERATION_TYPE_BOND_TAX_PROGRESSIVE', '33', 'Удержание налога по купонам по ставке 15%.'),
       ('OPERATION_TYPE_DIVIDEND_TAX_PROGRESSIVE', '34', 'Удержание налога по дивидендам по ставке 15%.'),
       ('OPERATION_TYPE_BENEFIT_TAX_PROGRESSIVE', '35', 'Удержание налога за материальную выгоду по ставке 15%.'),
       ('OPERATION_TYPE_TAX_CORRECTION_PROGRESSIVE', '36', 'Корректировка налога по ставке 15%.'),
       ('OPERATION_TYPE_TAX_REPO_PROGRESSIVE', '37', 'Удержание налога за возмещение по сделкам РЕПО по ставке 15%.'),
       ('OPERATION_TYPE_TAX_REPO', '38', 'Удержание налога за возмещение по сделкам РЕПО.'),
       ('OPERATION_TYPE_TAX_REPO_HOLD', '39', 'Удержание налога по сделкам РЕПО.'),
       ('OPERATION_TYPE_TAX_REPO_REFUND', '40', 'Возврат налога по сделкам РЕПО.'),
       ('OPERATION_TYPE_TAX_REPO_HOLD_PROGRESSIVE', '41', 'Удержание налога по сделкам РЕПО по ставке 15%.'),
       ('OPERATION_TYPE_TAX_REPO_REFUND_PROGRESSIVE', '42', 'Возврат налога по сделкам РЕПО по ставке 15%.'),
       ('OPERATION_TYPE_DIV_EXT', '43', 'Выплата дивидендов на карту.'),
       ('OPERATION_TYPE_TAX_CORRECTION_COUPON', '44', 'Корректировка налога по купонам.'),
       ('OPERATION_TYPE_CASH_FEE', '45', 'Комиссия за валютный остаток.'),
       ('OPERATION_TYPE_OUT_FEE', '46', 'Комиссия за вывод валюты с брокерского счёта.'),
       ('OPERATION_TYPE_OUT_STAMP_DUTY', '47', 'Гербовый сбор.'),
       ('OPERATION_TYPE_OUTPUT_SWIFT', '50', 'SWIFT-перевод.'),
       ('OPERATION_TYPE_INPUT_SWIFT', '51', 'SWIFT-перевод.'),
       ('OPERATION_TYPE_OUTPUT_ACQUIRING', '53', 'Перевод на карту.'),
       ('OPERATION_TYPE_INPUT_ACQUIRING', '54', 'Перевод с карты.'),
       ('OPERATION_TYPE_OUTPUT_PENALTY', '55', 'Комиссия за вывод средств.'),
       ('OPERATION_TYPE_ADVICE_FEE', '56', 'Списание оплаты за сервис Советов.'),
       ('OPERATION_TYPE_TRANS_IIS_BS', '57', 'Перевод ценных бумаг с ИИС на брокерский счёт.'),
       ('OPERATION_TYPE_TRANS_BS_BS', '58', 'Перевод ценных бумаг с одного брокерского счёта на другой.'),
       ('OPERATION_TYPE_OUT_MULTI', '59', 'Вывод денежных средств со счёта.'),
       ('OPERATION_TYPE_INP_MULTI', '60', 'Пополнение денежных средств со счёта.'),
       ('OPERATION_TYPE_OVER_PLACEMENT', '61', 'Размещение биржевого овернайта.'),
       ('OPERATION_TYPE_OVER_COM', '62', 'Списание комиссии.'),
       ('OPERATION_TYPE_OVER_INCOME', '63', 'Доход от оверанайта.'),
       ('OPERATION_TYPE_OPTION_EXPIRATION', '64', 'Экспирация опциона.'),
       ('OPERATION_TYPE_FUTURE_EXPIRATION', '65', 'Экспирация фьючерса.');