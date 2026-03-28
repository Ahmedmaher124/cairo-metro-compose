package domain.usecase



class CalculateFairUseCase() {
    operator fun invoke(count: Int): Int {
        return when  {
            count <= 9 -> 10
            count <= 19 -> 15
            else-> 20


        }
    }
}