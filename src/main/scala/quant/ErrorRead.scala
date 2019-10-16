package quant

sealed trait ErrorRead
final case object FileDontExist extends ErrorRead
